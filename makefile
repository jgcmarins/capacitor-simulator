
##
# 
# Sun Jul 12 21:20:07 BRT 2015
# author: Joao Gustavo Cabral de Marins
# e-mail: jgcmarins@gmail.com
# 
##

all: clean compile jar
clean:
	rm -rf build
	mkdir build
	cp -r images build
compile:
	javac -cp build src/capacitor/**/*.java -d build
cleanjar:
	rm -rf package
	mkdir package
jar: cleanjar
	echo "Main-Class: capacitor.main.Main" > build/manifest.txt
	jar -cfvm package/capacitor.jar build/manifest.txt -C build .
run:
	java -jar package/capacitor.jar
