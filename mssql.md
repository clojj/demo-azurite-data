
    docker run -e 'ACCEPT_EULA=Y' -e 'MSSQL_SA_PASSWORD=SqlServer123' -p 1433:1433 -v sqlvolume:/var/opt/mssql --name mssql -d mcr.microsoft.com/mssql/server:2019-CU5-ubuntu-18.04

    docker [start/stop] mssql