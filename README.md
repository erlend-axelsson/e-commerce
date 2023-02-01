# e-commerce

## setup

### prerequisite:

Written in Java 17, so you need a similarly modern jdk
to compile this repo

### recommended:

Most modern IDEs should be able to detect gradle and let
you run the application from there.

### Old fashioned "hard way":

Run this command in the root directory

> ./gradlew bootJar

Then run this command in ./build/libs

> java -jar ./e-commerce-0.0.1-SNAPSHOT.jar

## how I approached it:

I went for a fairly simple and battle tested stack.
Spring Boot may be a bit heavy for such a small application,
but it comes bundled with a bunch of stuff that makes it less
tedious to get up and running.

The discount interface was made so that it would be quite
easy to extend the available discounts beyond x of item,
and could be used to handle use cases where you only get
one discount regardless of the number of items, cases which
apply to a specific combination of items, or cases where it
deals with the total sum of the order for example VAT or a
percentage discount. Though in the percentage case the order
of operations would be important.

## what I would improve:

If I seriously intended to use this as an e-commerce application
then I would make several changes including but not limited to:

* I'd use something like Joda Money instead of integers
* I'd add authentication and authorization
* I'd have the products imported from a DB instead of hard coding it in the application
* I'd make a system for stakeholders to add/remove products and discounts so they wouldn't have to go to the devs to change pricing.
* I'd add a dockerfile and a manifest for some ci/cd pipeline (leaning towards github actions, but whatever the org is using).
* Add more logging/dashboards/alerts etc.
