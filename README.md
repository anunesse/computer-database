computer-database
=================
# How to contribute

## 1: Prerequisites

* [npm](https://npmjs.org/)
* [bower](http://bower.io)
* [grunt+grunt-cli](http://gruntjs.com/getting-started)
* [ruby](http://www.ruby-lang.org)
* [sass](http://sass-lang.com/install)
* [tomcat](http://tomcat.apache.org/)
* [apktool](https://code.google.com/p/android-apktool/downloads/list) with dependencies and helper script
* [a gread IDE](http://www.jetbrains.com/idea/)  

## 2: Build the project

```mvn clean install```

> This will need npm because it will run ```grunt```.

## 3: Import the project into IntelliJ

It's a maven project, so just import it as such.

## 4: Prepare the database

We can't use automatic schema migration in production, but we can use [FlyWay](http://flywaydb.org/getstarted/firststeps/maven.html) to automatically migrate our local development database on demand.

```mvn flyway:migrate```

## 5: Configure HTTP Server

### Case 1: Apache

Edit **/etc/hosts**
```
    127.0.0.1       localhost designmyapp-dev
```

Create file **/etc/httpd/conf.d/designmyapp-dev.conf**
```
<Virtualhost *:80>
   ServerName designmyapp-dev

   <Proxy http://designmyapp-dev>
     Order deny,allow
     Allow from all
   </Proxy>

   ProxyPass           /rest   http://designmyapp-dev:8080/rest
   ProxyPassReverse    /rest   http://designmyapp-dev:8080/rest

   ProxyPass           /       http://designmyapp-dev:9000/
   ProxyPassReverse    /       http://designmyapp-dev:9000/
</Virtualhost>
```

### Case 2: Nginx

Edit **/etc/hosts**
```
    127.0.0.1       localhost designmyapp-dev
```

Create a new site (or edit default site) **/etc/nginx/nginx.conf** with following config:
```
upstream designmyapp-dev-grunt {
        server  127.0.0.1:9000;
}

upstream designmyapp-dev-tomcat {
	server 127.0.0.1:8080;
}

server {
        listen   80;
        server_name 127.0.0.1;

        location / {
                proxy_pass http://designmyapp-dev-grunt;
        }

	location /rest/ {
		proxy_pass http://designmyapp-dev-tomcat;
	}

}
```

## 5: Project configuration
In GeolocKickStartR/etc, make a copy of geoloc-kickstartr.properties to user-config.properties.
Update the "email.contact" field with your own and only keep entries containing "override with user-config.properties" as a value.
Sample user-config.properties file:

```
# Local setup configuration
environment=dev
environment.base.url=http://designmyapp-dev
application.template.default.dir=/opt/geoloc-kickstartr/templates/default
application.template.synchronizable.dir=/opt/geoloc-kickstartr/templates/synchronizable
application.work.dir=/opt/geoloc-kickstartr/work
apktool.dir=/opt/apktool1.5.2
keystore.path=/home/www-data/.android/debug.keystore
default-images.dir=/opt/geoloc-kickstartr/default-images
upload.dir=/opt/geoloc-kickstartr/upload
howtoinstall.url=http://localhost:8080/#/howto
email.contact=my@email.com

# Paypal identifiers
paypal.url.payment=https://www.sandbox.paypal.com/cgi-bin/webscr
paypal.url.notify=http://my-public-url.excilys.com/rest/paypal?key={key}
paypal.url.return=http://localhost:8080/#/key/{key}
paypal.business=my-test@myemail.com
paypal.environment=www.sandbox
```

All directories defined above in **opt/** must be created and be writable (chmod 777).
Put the **apktool1.5.2** folder you extracted inside **opt/**.
For keystore.path, **www-data/** is your user directory. And to have the debug.keystore file, you must have compiled an Android project at least once.
In tomcat conf directory, edit catalina.properties and set the shared.loader property to your GeolocKickstartR/etc folder
DO NOT put a slash '/' at the of environment.base.url's value.

## 6: Startup

To run the frontend with live reload:

```
cd geoloc-kickstartr-web/yo
grunt server
```

Server will be accessible at http://localhost:80

After that, any change to a front end file will automatically reload the browser page!


## Extras

### Template creation procedure

>In openparis project, checkout the desired branch (geoloc-kickstartr, geoloc-kickstartr-synchronized ...) and run mvn clean install -Pgeoloc
N.B.: There shouldn't be any android asset or resource not supposed to be in the final project (i.e.: test resources) 

>Copy generated APK to a temporary folder, then decompile APK with apktool:
```
apktool d -s openparis-1.X.X-SNAPSHOT.apk
```

>Edit AndroidManifest.xml and replace versionName, applicationPackage strings with properties (${versionName}), and force the declaration of activity classes with an absolute package path (com.ebusinessinformation...).
See other templates samples to make sure you didn't miss any element

>Edit strings.xml and replace app_name value with ${applicationName}

>Move ./assets folder in ./build/apk/assets

>Drop fontawesome ttf file in ./build/apk
