# Azure Deployment Guide (Direct JAR)

This guide walks you through deploying your Spring Boot app directly to **Azure Web Apps** as a **Java application**.
**No Docker or Container Registry is required.**

---

## Part 1: Create Web App on Azure

1.  **Search**: Search for **"App Services"**.
2.  **Create**: Click **+ Create** -> **Web App**.
3.  **Basics**:
    *   **Resource group**: Create new (e.g., `medibook-rg`).
    *   **Name**: Unique name (e.g., `medibook-api`).
    *   **Publish**: Select **Code** (NOT Docker Container).
    *   **Runtime stack**: Select **Java 17**.
    *   **Java web server stack**: Select **Java SE (Embedded Web Server)**.
    *   **Operating System**: **Linux**.
    *   **Region**: e.g., "East US".
    *   **Pricing Plan**: Select **Basic B1** (recommended) or **Free F1** (if available).
4.  **Review + create**: Click **Create**.

---

## Part 2: Connect Application to Aiven DB

Since we removed the hardcoded credentials from the code, you **MUST** set these in Azure.

1.  Go to your new **Web App** (`medibook-api`).
2.  Under **Settings**, click **Environment variables**.
3.  Click **+ Add** for each of these:

    | Name | Value |
    |------|-------|
    | `SPRING_DATASOURCE_URL` | `jdbc:mysql://medibook-tapeshchavle12.d.aivencloud.com:26818/defaultdb?sslMode=REQUIRED` |
    | `SPRING_DATASOURCE_USERNAME` | `avnadmin` |
    | `SPRING_DATASOURCE_PASSWORD` | `<your-aiven-password>` |
    | `RAZORPAY_KEY_ID` | `<your-razorpay-key>` |
    | `RAZORPAY_KEY_SECRET` | `<your-razorpay-secret>` |
    | `JWT_SECRET` | `<your-jwt-secret>` |
    | `PORT` | `8080` |

4.  Click **Apply** -> **Confirm**.

> **Note for Local Testing:** If you want to run the app properly on your local machine again, you will now need to set these environment variables in your IDE (IntelliJ/Eclipse) or revert `application.properties` temporarily.

---

## Part 3: Setup GitHub Deployment

1.  **Get Publish Profile**:
    *   On your Web App's **Overview** page, click **"Download publish profile"**.
    *   Open the file and copy the **entire XML content**.

2.  **Configure GitHub Secrets**:
    *   Go to your GitHub Repo -> **Settings** -> **Secrets and variables** -> **Actions**.
    *   Add this **New repository secret**:

    | Name | Value |
    |------|-------|
    | `AZURE_WEBAPP_PUBLISH_PROFILE` | (Your XML Content) |

    *(Note: You do NOT need ACR_USERNAME or ACR_PASSWORD anymore).*

3.  **Update Workflow File**:
    *   Open `.github/workflows/deploy.yml`.
    *   Update `AZURE_WEBAPP_NAME` to your exact Web App name.
    *   **Commit and Push** to `main`.

---

## Done!

Once you push:
1.  GitHub Actions will build your JAR file.
2.  It will upload the JAR directly to Azure.
3.  Azure will detect it's a Spring Boot app and run it.
