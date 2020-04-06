if [ -z "$1" ]
then
LIB_PATH="lib"
else
LIB_PATH=$1
fi

download_jar()
{
  LIB_JAR=$1
  LIB_URL=$2
  echo "Downloading $LIB_JAR"
  echo "from $LIB_URL"
  curl -o $LIB_PATH/$LIB_JAR $LIB_URL
}

echo "Setting up environment..."

rm -rf $LIB_PATH
mkdir $LIB_PATH

echo "Downloading into $LIB_PATH"
download_jar easymock-4.2.jar \
https://repo1.maven.org/maven2/org/easymock/easymock/4.2/easymock-4.2.jar
download_jar objenesis-3.1.jar \
https://repo1.maven.org/maven2/org/objenesis/objenesis/3.1/objenesis-3.1.jar
download_jar junit-4.13.jar \
https://repo1.maven.org/maven2/junit/junit/4.13/junit-4.13.jar
download_jar hamcrest-2.2.jar \
https://repo1.maven.org/maven2/org/hamcrest/hamcrest/2.2/hamcrest-2.2.jar

echo "Download Complete"