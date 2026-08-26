FROM mcr.microsoft.com/devcontainers/base:ubuntu-24.04

WORKDIR /workspace

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        openjdk-21-jdk \
        maven \
        git \
        curl \
        openssh-client \
        ca-certificates \
    && rm -rf /var/lib/apt/lists/*
