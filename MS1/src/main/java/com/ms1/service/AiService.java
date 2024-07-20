package com.ms1.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import com.google.protobuf.ByteString;
import com.ms1.mapper.AiMapper;

@Service
public class AiService {
    
    private AiMapper aiMapper;

    public AiService(AiMapper aiMapper) {
        this.aiMapper = aiMapper;
    }

    @Value("${google.cloud.project-id}")
    private String projectId;

    @Value("${google.cloud.private-key-id}")
    private String privateKeyId;

    @Value("${google.cloud.private-key}")
    private String privateKey;

    @Value("${google.cloud.client-email}")
    private String clientEmail;

    @Value("${google.cloud.client-id}")
    private String clientId;

    @Value("${google.cloud.auth-uri}")
    private String authUri;

    @Value("${google.cloud.token-uri}")
    private String tokenUri;

    @Value("${google.cloud.auth-provider-cert-url}")
    private String authProviderCertUrl;

    @Value("${google.cloud.client-cert-url}")
    private String clientCertUrl;

    public String uploadImage(MultipartFile file) throws IOException, JSONException {
        List<AnnotateImageRequest> requests = new ArrayList<>();
        ByteString imgBytes = ByteString.readFrom(file.getInputStream());
        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        JSONObject credentialsJson = new JSONObject();
        credentialsJson.put("type", "service_account");
        credentialsJson.put("project_id", projectId);
        credentialsJson.put("private_key_id", privateKeyId);
        credentialsJson.put("private_key", privateKey.replace("\\n", "\n"));
        credentialsJson.put("client_email", clientEmail);
        credentialsJson.put("client_id", clientId);
        credentialsJson.put("auth_uri", authUri);
        credentialsJson.put("token_uri", tokenUri);
        credentialsJson.put("auth_provider_x509_cert_url", authProviderCertUrl);
        credentialsJson.put("client_x509_cert_url", clientCertUrl);

        GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(credentialsJson.toString().getBytes()));

        ImageAnnotatorSettings settings =
            ImageAnnotatorSettings.newBuilder()
            .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
            .build();

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create(settings)) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            StringBuilder result = new StringBuilder();
            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    throw new RuntimeException("Error: " + res.getError().getMessage());
                }
                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    result.append(annotation.getDescription());
                    result.append("\n");
                }
            }
            return result.toString();
        }
    }


	public int requestImg() {
		return aiMapper.requestImg();
	}


	public int insertRequest() {
		return aiMapper.insertRequest();
	}


	public int insertDetected(String detectedText, String id) {
		return aiMapper.insertDetected(detectedText, id);
	}


	public List<String> selectDetected(String id) {
		return aiMapper.selectDetected(id);
	}




}
