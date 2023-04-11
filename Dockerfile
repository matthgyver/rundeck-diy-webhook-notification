FROM openjdk:8

WORKDIR /home/rundeck-diy-webhook-notification-plugin
CMD ["bash", "./gradlew"]