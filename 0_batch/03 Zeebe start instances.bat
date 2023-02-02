@echo off

:: it uses Zeebe CLI client
set DATA_1="{\"impact\": \"medium\", \"urgency\": \"high\"}"
set DATA_2="{\"impact\": \"medium\", \"urgency\": \"medium\"}"
set DATA_3="{\"impact\": \"low\",    \"urgency\": \"medium\"}"
set APPL=d:\tools\camunda-zeebe-cli\zbctl.exe
set ZEEBE_ADDRESS=127.0.0.1:26500
set ZEEBE_INSECURE_CONNECTION=true

%APPL% status
echo --------------------------------------------------
%APPL% create instance TriageProcess_01 --variables %DATA_1%
echo --------------------------------------------------
%APPL% create instance TriageProcess_01 --variables %DATA_2%
echo --------------------------------------------------
%APPL% create instance TriageProcess_01 --variables %DATA_3%
pause