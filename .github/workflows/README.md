## Overview
We have two different workflows in our project:
1. **Model Deployment**

This workflow is build using the `deploy-model-api.yml` file and it's purpose is to automate model api deployment from building the docker image to deploying the image on cloud run.

2. **CRUD API Deployment**

This workflow is build using the `deploy-crud-api.yml` file and it's purpose is to automate crud api deployment from building the docker image to deploying the image on cloud run.

To replicate this workflows with your own GCP services, you need to follow the step from this link (https://cloud.google.com/blog/products/devops-sre/deploy-to-cloud-run-with-github-actions/).