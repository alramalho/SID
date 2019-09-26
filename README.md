# Overview
Last year academic project consisting of communication between Databases (MongoDB and SQL).
Objective was merely monitor a Temperature/Lightning sensor in ISCTE (Our University). This sensor published in a broker and our application was supposed to read from it.

# Recetor
Received the JSON string from the ISCTE sensor and parsed it, then it should write it to mongoDB (opened in localhost).

# Migrador
This should be the application responsible for transferring the data over to SQL

# Sensor
This was a self-developed sensor simulation that I created in order to keep sending dummys for debugging.
