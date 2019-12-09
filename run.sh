curl -L "https://github.com/docker/compose/releases/download/1.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
ls -la /usr/local/bin
docker-compose up
docker-compose -f docker-compose.yml -f docker-compose.qa.yml up
