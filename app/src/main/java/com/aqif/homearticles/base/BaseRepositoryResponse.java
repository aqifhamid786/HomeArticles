package com.aqif.homearticles.base;


/**
 *
 * Base reponse model for (data) repositories.
 *
 */
public class BaseRepositoryResponse<T> {

        private String message;
        private T data;

        public BaseRepositoryResponse(String message, T data){
            this.message = message;
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public T getData() {
            return data;
        }
}
