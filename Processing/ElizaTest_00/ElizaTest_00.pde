import codeanticode.eliza.*;

Eliza eliza;
eliza = new Eliza(this);
eliza.readScript("http://chayden.net/eliza/script");

String response = eliza.processInput("Hello");
println(response);

response = eliza.processInput("My day was fine, just another boring day at work");
println(response);

response = eliza.processInput("yes it was");
println(response);
