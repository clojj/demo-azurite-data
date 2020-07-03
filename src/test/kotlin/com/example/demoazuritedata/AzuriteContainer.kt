package com.example.demoazuritedata

import org.testcontainers.containers.GenericContainer

class AzuriteContainer : GenericContainer<AzuriteContainer>("mcr.microsoft.com/azure-storage/azurite")