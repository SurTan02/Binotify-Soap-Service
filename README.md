# Binotify SOAP Service

## DESCRIPTION

1. Fitur logging untuk setiap request yang masuk ke service soap.
2. API KEY untuk memanggil SOAP Service/Subscription Service (termasuk callback request ke Binotify App).
3. Menerima Permintaan Subscription dari Binotify App
4. Menerima Penerimaan / Penolakan Permintaan Subscription
5. Endpoint Check Status Permintaan

## DATABASE SCHEMA

Berikut adalah skema basis data yang digunakan pada Tugas Besar ini:

1. Tabel Logging (id (PK) , description, IP, endoint, requested_at)
2. Tabel Subscription (creator_id (PK) , subscriber_id (PK), status)

## PEMBAGIAN TUGAS

1. SECURITY: 13520113
2. DATABASE: 13520059
3. MENERIMA PERMINTAAN SUBSCRIPTION: 13520056
4. MENERIMA PENERIMAAN / PENOLAKAN PERMINTAAN SUBSCRIPTION: 13520059
5. ENDPOINT CHECK STATUS PERMINTAAN: 13520113
