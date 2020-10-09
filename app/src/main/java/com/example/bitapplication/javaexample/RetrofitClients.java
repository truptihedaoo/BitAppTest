package com.example.bitapplication.javaexample;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClients {


        private static RetrofitClients instance = null;
        private Api myApi;

        private RetrofitClients() {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.coindesk.com/v1/bpi/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            myApi = retrofit.create(Api.class);
        }

        public static synchronized RetrofitClients getInstance() {
            if (instance == null) {
                instance = new RetrofitClients();
            }
            return instance;
        }

        public Api getMyApi() {
            return myApi;

    }
}
