        Log.e("ActiveUser", loggedInUser);

        //get initial active chat
        ActiveChatRequest activeChatRequest = new ActiveChatRequest(loggedInUser, activeChatListener);
        RequestQueue queue = Volley.newRequestQueue(MenuActivity.this);

        queue.add(activeChatRequest);
        Log.e("ActiveChat", activeChat +"");

        getMessageRunnable.run();

        if (initialMessageRequest) {
            //requests the initial messages
            MessageGetRequest messageGetRequest = new MessageGetRequest(activeChat, responseListener);
            RequestQueue queue = Volley.newRequestQueue(MenuActivity.this);

            queue.add(messageGetRequest);
            setInitialMessageRequest(false);

        }


        getMessageRunnable.run();





////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





         //gets the users active chat
        activeChatListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    int success = jsonResponse.getInt("success");
                    Log.e("Response: ", response);

                    // if server says it succeded do this
                    if (success == 1) {
                        setActiveChat(jsonResponse.getInt("activechat"));

                    } else {


                        //displays error message from server in not success
                        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                        builder.setMessage(jsonResponse.getString("message"))
                                .setNegativeButton("OK", null)
                                .create()
                                .show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MenuActivity.this, "Error fetching active chat id!", Toast.LENGTH_SHORT).show();
                }
            }
        };