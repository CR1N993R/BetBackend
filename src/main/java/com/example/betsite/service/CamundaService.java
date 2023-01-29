package com.example.betsite.service;

import com.example.betsite.dto.CurrentStateOfProcessDTO;
import com.example.betsite.dto.GetVariableDTO;
import com.example.betsite.dto.StartProcessDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CamundaService {
    private final HttpHeaders httpHeaders;
    private final RestTemplate rest;

    public String startProcess() {
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>("{}", httpHeaders);
            ResponseEntity<StartProcessDTO> responseEntity = rest.exchange("http://localhost:8080/engine-rest/process-definition/key/UserStates/start", HttpMethod.POST, requestEntity, StartProcessDTO.class);
            return responseEntity.getBody().getId();
        } catch (Exception e) {}
        return null;
    }

    public CurrentStateOfProcessDTO getCurrentStateOfProcess(String processId) {
        try {
            ResponseEntity<CurrentStateOfProcessDTO[]> responseEntity = rest.exchange("http://localhost:8080/engine-rest/task?processInstanceId=" + processId, HttpMethod.GET, null, CurrentStateOfProcessDTO[].class);
            if (responseEntity.getBody() != null && responseEntity.getBody().length > 0){
                return responseEntity.getBody()[0];
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void completeTask(String processId, String body) {
        CurrentStateOfProcessDTO currentState = getCurrentStateOfProcess(processId);
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(body, httpHeaders);
            rest.exchange("http://localhost:8080/engine-rest/task/" + currentState.getId() + "/complete", HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void completeTask(String processId) {
        completeTask(processId, "{}");
    }

    public void completeTask(String processId, String argumentName, String argumentValue) {
        String body = "{\"variables\":{\"" + argumentName + "\":{\"value\":\""+ argumentValue + "\"}}}";
        completeTask(processId, body);
    }

    public void deleteProcessInstance(String processId) {
        try {
            rest.exchange("http://localhost:8080/engine-rest/process-instance/" + processId, HttpMethod.DELETE, null, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addVariableToProcess(String varName, String varValue, String processId) {
        String body = "{\"value\": \"" + varValue + "\",\"type\": \"String\"}";
        try {
            HttpEntity<String> requestEntity = new HttpEntity<>(body, httpHeaders);
            rest.exchange("http://localhost:8080/engine-rest/process-instance/" + processId + "/variables/" + varName, HttpMethod.PUT, requestEntity, String.class);
        } catch (Exception e) {}
    }

    public void removeVariableFromProcess(String varName, String processId) {
        try {
            rest.exchange("http://localhost:8080/engine-rest/process-instance/" + processId + "/variables/" + varName, HttpMethod.DELETE, null, String.class);
        } catch (Exception e) {}
    }

    public String getVariableFromProcess(String varName, String processId) {
        try {
            ResponseEntity<GetVariableDTO> responseEntity = rest.exchange("http://localhost:8080/engine-rest/process-instance/" + processId + "/variables/" + varName, HttpMethod.GET, null, GetVariableDTO.class);
            return responseEntity.getBody().getValue();
        } catch (Exception e) {}
        return "";
    }
}
