echo on
xcopy /s c:\progs\com\cs490 c:\progs\tomcat8\webapps\webserv1\WEB-INF\classes\com\cs490
cd c:\progs\tomcat8\bin
shutdown.bat
starup.bat
pause