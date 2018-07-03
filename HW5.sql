

#1. What are the #prods whose name begins with a ¡¯p¡¯ and are less than $300.00?
SELECT prod_id FROM Product WHERE price<300 AND pname LIKE 'p%';


#2 Names of the products stocked in ¡±d2¡±.*
#(a) without in/not in
SELECT pname FROM Product P,Stock S WHERE P.prod_id = S.prod_id AND dep_id='d2';
#(b) with in/not in
SELECT pname FROM Product P WHERE P.prod_id IN(SELECT S.prod_id FROM Stock S WHERE dep_id='d2');

#3. #prod and names of the products that are out of stock.*
#(a) without in/not in 
SELECT P.prod_id, pname FROM Product P, Stock S WHERE P.prod_id=S.prod_id AND quantity<=0 ;
#(b) with in/not in
SELECT P.prod_id, pname FROM Product P WHERE P.prod_id IN(SELECT S.prod_id FROM Stock S WHERE quantity<=0);

#4. Addresses of the depots where the product ¡±p1¡± is stocked.*
#(a) without exists/not exists and without in/not in 
SELECT DISTINCT addr FROM Depot D, Stock S WHERE D.dep_id=S.dep_id AND S.prod_id=¡¯p1¡¯;
#(b) with in/not in
SELECT DISTINCT addr FROM Depot WHERE dep_id IN (SELECT dep_id FROM STOCK WHERE prod_id=¡¯p1¡¯);
#(c) with exists/not exists
SELECT DISTINCT addr FROM Depot D WHERE EXISTS(SELECT S.dep_id FROM Stock S WHERE D.dep_id = S.dep_id AND prod_id='p1');

#5. #prods whose price is between $250.00 and $400.00.*
#(a) using intersect.
SELECT prod_id FROM product WHERE price >= 250
 INTERSECT 
SELECT prod_id FROM product WHERE price < 400;
#(b) without intersect.
SELECT prod_id FROM Product WHERE price>=250 AND price<=400;

#6. How many products are out of stock?*
SELECT -SUM(quantity) FROM Stock WHERE quantity<=0;

#7. Average of the prices of the products stocked in the ¡±d2¡± depot.*
SELECT AVG(price) AS average_price FROM product p, stock s WHERE p.prod_id = s.prod_id AND dep_id = 'd2';

#8. #deps of the depot(s) with the largest capacity (volume).*
SELECT dep_id FROM depot WHERE volume IN (SELECT MAX(volume) FROM depot);

#9. Sum of the stocked quantity of each product.*
SELECT prod_id,SUM(quantity) FROM Stock GROUP BY prod_id;

#10. Products names stocked in at least 3 depots.*
#(a) using count 
SELECT pname FROM product WHERE prod_id IN (SELECT prod_id FROM stock GROUP BY prod_id HAVING COUNT(dep_id) >= 3);
#(b) without using count
SELECT pname FROM product WHERE prod_id IN (SELECT s1.prod_id FROM stock s1, stock s2, stock s3 WHERE s1.dep_id <> s2.dep_id AND s2.dep_id <> s3.dep_id AND s3.dep_id <> s1.dep_id AND s1.prod_id = s2.prod_id= s3.prod_id);

#11. #prod stocked in all depots.*
#(a) using count 
SELECT prod_id FROM stock GROUP BY prod_id HAVING COUNT(dep_id)=(SELECT COUNT(*) FROM depot) ;

#(b) using exists/not exist
SELECT prod_id FROM product P WHERE EXISTS(SELECT prod_id FROM stock S WHERE P.prod_id=S.prod_id 
GROUP BY prod_id 
HAVING COUNT(dep_id)=(SELECT COUNT(*) FROM depot ));
