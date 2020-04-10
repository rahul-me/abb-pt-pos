mkdir build
cd ..
javac -d "abb_pt_pos\build" -cp abb_pt_java\build\abb_pt_java.jar abb_pt_pos\src\com\gridscape\nayax\PosTerminal.java abb_pt_pos\src\com\gridscape\nayax\impl\*.java
cd abb_pt_pos\build
jar -cf abb_pt_pos.jar com\gridscape\nayax\impl\*.class com\gridscape\nayax\PosTerminal.class
rm -rf com
cd..
javac -d "abb_pt_pos\build" -cp abb_pt_java\build\abb_pt_java.jar abb_pt_pos\src\com\gridscape\nayax\PosTerminal.java abb_pt_pos\src\com\gridscape\nayax\impl\*.java 
cd abb_pt_pos\
cp -r pre-req\*.* build\
cd build
jar -cfm abb_pt_pos.jar MANIFEST.MF com\gridscape\nayax\impl\*.class com\gridscape\nayax\PosTerminal.class org/eclipse/jdt/internal/jarinjarloader/*.class
rm -r com
rm -r com
rm -r org
rm -r MANIFEST.MF
cd ..
cp lib\nrjavaserial-3.11.0.jar build\
cp lib\abb_pt_java.jar build\
cp logging.properties build\