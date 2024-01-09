package com.project.shopapp.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " could not be found for id: " + id);
    }

    public ResourceNotFoundException(String resource, String uuid) {
        super(resource + " could not be found for uuid: " + uuid);
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }


}