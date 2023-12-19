create database Carrental;
use Carrental;
drop database Carrental;


-- Vehicle Table
create table vehicle (
vehicleID int primary key auto_increment,
make varchar(255),
model varchar(255),
year int,
dailyRate double,
status varchar(20),
passengerCapacity int,
engineCapacity int
);
select * from vehicle;

create table customer (
customerID int primary key,
firstName varchar(255),
lastName varchar(255),
email varchar(255),
phoneNumber varchar(15)
);

create table lease (
leaseID int primary key,
vehicleID int,
customerID int,
startDate date,
endDate date,
type varchar(20),
foreign key (vehicleID) references vehicle(vehicleID),
foreign key (customerID) references customer(customerID)
);

create table payment (
paymentID int primary key,
leaseID int,
paymentDate date,
amount double,
foreign key (leaseID) references lease(leaseID)
);

