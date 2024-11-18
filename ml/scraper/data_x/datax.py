# -*- coding: utf-8 -*-
"""datax

Automatically generated by Colab.

Original file is located at
    https://colab.research.google.com/drive/1_LubqWAH6KyeYrr5A2vlzBDtU_BTuBpf
"""

#@title Twitter Auth Token

twitter_auth_token = 'xxxx' #ganti menggunakan auth token akun yang akan digunakan

# Import required Python package
!pip install pandas
import pandas as pd
import time

# Install Node.js (because tweet-harvest built using Node.js)
!sudo apt-get update
!sudo apt-get install -y ca-certificates curl gnupg
!sudo mkdir -p /etc/apt/keyrings
!curl -fsSL https://deb.nodesource.com/gpgkey/nodesource-repo.gpg.key | sudo gpg --dearmor -o /etc/apt/keyrings/nodesource.gpg

!NODE_MAJOR=20 && echo "deb [signed-by=/etc/apt/keyrings/nodesource.gpg] https://deb.nodesource.com/node_$NODE_MAJOR.x nodistro main" | sudo tee /etc/apt/sources.list.d/nodesource.list

!sudo apt-get update
!sudo apt-get install nodejs -y

!node -v

# Crawl Data
keywords = []
with open('keywords.txt', 'r') as file:
    keywords = [line.strip() for line in file if line.strip()]

filename = 'datax.csv'
limit = 5000

for keyword in keywords:
    search_keyword = f'{keyword} lang:id'
    temp_filename = f'{keyword}.csv'

    # Menjalankan tweet-harvest untuk mengumpulkan data sementara ke file di temp_filepath
    !npx -y tweet-harvest@2.6.1 -o "{temp_filename}" -s "{search_keyword}" --tab "LATEST" -l {limit} --token {twitter_auth_token}
    print(f"Data untuk kata kunci '{keyword}' berhasil dikumpulkan")

    time.sleep(60)


print("Proses pencarian selesai untuk semua kata kunci")

import os
folder_path = 'tweets-data'
# Daftar untuk menyimpan dataframe
all_data = []

# Iterasi semua file .csv di dalam folder
for file_name in os.listdir(folder_path):
    if file_name.endswith('.csv'):
        file_path = os.path.join(folder_path, file_name)
        # Membaca file .csv dan menambahkannya ke list
        df = pd.read_csv(file_path)
        all_data.append(df)

# Menggabungkan semua dataframe dalam list menjadi satu
combined_data = pd.concat(all_data, ignore_index=True)

# Menyimpan hasil gabungan ke file .csv baru
combined_data.to_csv(f'{filename}', index=False)

# Membaca dataframe
df = pd.read_csv(f'{filename}', delimiter=",")

# Menampilkan dataframe
display(df)

# Cek jumlah data yang didapatkan

num_tweets = len(df)
print(f"Jumlah tweet dalam dataframe adalah {num_tweets}.")

df.info()

df.columns

# Menghapus semua kolom selain 'full_text'
df = df.drop(columns=[col for col in df.columns if col != 'full_text'])
print(df)

# Membaca data dari file CSV
df = pd.read_csv(f'{filename}')

# Ubah nama kolom 'full_text' jadi 'complaints'
df = df.rename(columns={'full_text': 'complaints'})

# Mengganti NaN dengan string kosong, lalu mengubah semua teks menjadi huruf kecil
df['complaints'] = df['complaints'].fillna("").apply(lambda x: x.lower() if isinstance(x, str) else x)


# Mendefinisikan keywords untuk kategorisasi
def label_data(complaint):

    # Dikategorikan berdasarkan keywords
    if "jalan rusak" in complaint or "jalan" in complaint:
        return "jalan rusak"
    elif "macet" in complaint:
        return "macet"
    elif any(word in complaint for word in ["publik", "umum", "fasilitas umum"]):
        return "fasilitas umum"
    elif any(word in complaint for word in ["lingkungan", "alam"]):
        return "lingkungan"
    elif any(word in complaint for word in ["polusi", "polusi udara", "pencemaran", "asap"]):
        return "polusi"
    elif "sampah" in complaint:
        return "sampah"
    elif "limbah" in complaint:
        return "limbah"
    elif any(word in complaint for word in ["air", "sanitasi", "sungai tercemar"]):
        return "air"
    elif any(word in complaint for word in ["kekeringan", "cuaca ekstrem", "krisis air"]):
        return "kekeringan"
    elif "hutan" in complaint:
        return "hutan"
    else:
        return "others"

df['category'] = df['complaints'].apply(label_data)

# Menyimpan hanya kolom 'complaints' dan 'category'
df = df[['complaints', 'category']]

df.to_csv(f'{filename}', index=False)
print(df.head())

df.columns