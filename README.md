# 📘 AMU Émargement Project 
The project applies and integrates Spring Boot, Jetpack Compose, and TypeScript, following MVC and MVVM architectural patterns for clean and scalable development.
**Project in early development -> UX to be redesigned**


this project is developped for students and professors at Aix-Marseille University (AMU) to manage course attendance in a secure, user-friendly, and modern way.

## 🎯 Project Overview

This application streamlines attendance management (`émargement`) by integrating **Single Sign-On (SSO)** and offering **three methods for course check-in**:

1. **Manual Code Entry**  
2. **QR Code Scanning**  
3. **Automatic BLE Beacon Detection**

The BLE Beacon method ensures enhanced **security and presence validation**, as it only works when the **professor's beacon is nearby**, preventing remote check-ins or spoofing.



## 🧩 Tech Stack

- **Frontend**: Kotlin + Jetpack Compose (Android), MVVM / Clean architecture, hilt DI
- **Backend**: Java spring boot/ Mysql, JPA/Hibernate ORM
- **Backend**: TypeScript

## 🚀 How It Works

1. Users log in using AMU's Single Sign-On (SSO).
2. Based on the authenticated user role (Student or Professor), a corresponding interface is presented:

Students see options to:

   - **Manual code** provided by the professor
   - **QR Code** displayed by the professor
   - **BLE Beacon Detection**, which is automatic but only works when the professor is nearby.


Professors see tools to:

   - Manage their courses and sessions
   - Generate and display QR codes for check-in



## 🎥 Demo Videos

### 👨‍🏫 Professor Interface Flow  
📺 [Watch on Google Drive](https://drive.google.com/file/d/1u10wCjtpb6-pX8k6IG8DfFxWSzDtMMGB/view?usp=sharing)

---

### 👨‍🎓 Student Interface Flow: QR Code Signing + Double Sign Prevention  
📺 [Watch on Google Drive](https://drive.google.com/file/d/1tzp1UL5rTGHK4edeZBICAS1OXZrjNxrl/view?usp=sharing)

---

### 📡 Student Interface Flow: BLE Beacon Signing  
📺 [Watch on Google Drive](https://drive.google.com/file/d/1trv81VYxVujE9YiGIRFWvqJbRNzqtAkl/view?usp=sharing)

---

### ❌ Close Sessions & Disconnect  
📺 [Watch on Google Drive](https://drive.google.com/file/d/1to7r_pcKrI9GKN9Sb7oGwkc1zcwy1VQN/view?usp=sharing)





