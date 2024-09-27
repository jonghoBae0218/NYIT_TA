package com.bers.nyittutorassistant;

import android.util.Log;

import com.google.gson.Gson;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    public static final String StorageConnectionString = "DefaultEndpointsProtocol=https;"
            + "AccountName=dreamstudy;"
            + "AccountKey=NV+ttpT3qnE4f2HoRuM03NIqcAeKj0M4kkZvqnvviEfRxDu+C5r12OcmJUgYKudwsyih3VQDuXPzJW8RTzUioA==";

    public static final String StorageQuestionDataContainer = "1024jongho";

    private Gson gson = new Gson();

    public Response<QuestionDataList> getQuestionData(String tutorName)  {
        try {
            CloudBlobContainer containerTutor = getQuestionDataContainer();
            CloudBlockBlob blobTutor = containerTutor.getBlockBlobReference(tutorName);

            if (blobTutor.exists()) {
                QuestionDataList questionDataList = gson.fromJson(blobTutor.downloadText(), QuestionDataList.class);
                return new Response.Success<>(questionDataList);
            } else {
                return new Response.NotExist<>();
             }
        } catch (Exception e) {
            return new Response.Failure<>();
        }
    }

    public Response<QuestionDataList> updateQuestionData(QuestionData questionData)  {
        CloudBlobContainer container;

        try {
            container = getQuestionDataContainer();

            CloudBlockBlob blob = container.getBlockBlobReference(questionData.tutorName);

            if (blob.exists()) {
                QuestionDataList questionDataList = gson.fromJson(blob.downloadText(), QuestionDataList.class);
                questionDataList.data.add(0, questionData);
                blob.uploadText(gson.toJson(questionDataList));
            } else {
                List<QuestionData> list = new ArrayList<>();
                list.add(questionData);
                QuestionDataList questionDataList = new QuestionDataList(list);
                blob.uploadText(gson.toJson(questionDataList));
            }

            return new Response.Success<>(new QuestionDataList(new ArrayList<>()));
        } catch (Exception e) {
            Log.e("minmin", "" + e.toString());
            return new Response.Failure<>();
        }
    }

    private CloudBlobContainer getQuestionDataContainer() throws URISyntaxException, InvalidKeyException, StorageException {
        CloudStorageAccount account = CloudStorageAccount.parse(StorageConnectionString);
        CloudBlobClient blobClient = account.createCloudBlobClient();
        return blobClient.getContainerReference(StorageQuestionDataContainer);
    }
}
