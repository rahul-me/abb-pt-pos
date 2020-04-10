cd..
javac -d "abb_pt_pos\build" -cp abb_pt_java\build\abb_pt_java.jar abb_pt_pos\src\com\gridscape\nayax\PosTerminal.java abb_pt_pos\src\com\gridscape\nayax\impl\*.java 
cd abb_pt_pos\
xcopy /s /Q pre-req\*.* build\
cd build
jar -cfm abb_pt_pos.jar MANIFEST.MF com\gridscape\nayax\impl\*.class com\gridscape\nayax\PosTerminal.class org/eclipse/jdt/internal/jarinjarloader/*.class
del /f /Q com
rd /S /Q com
rd /S /Q org
del /Q MANIFEST.MF
cd ..
copy lib\nrjavaserial-3.11.0.jar build\
copy lib\abb_pt_java.jar build\
copy logging.properties build\