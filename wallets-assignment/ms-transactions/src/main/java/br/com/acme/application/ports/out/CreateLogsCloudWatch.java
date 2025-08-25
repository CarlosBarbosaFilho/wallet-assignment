package br.com.acme.application.ports.out;

public interface CreateLogsCloudWatch {

     void sendLog(String message);
}
