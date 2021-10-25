CREATE TABLE BOOK (ID INT IDENTITY PRIMARY KEY,
NAME VARCHAR(200),
DESCRIPTION VARCHAR(500),
AUTHOR VARCHAR(255),
BOOK_TYPE VARCHAR(255),
PRICE DOUBLE,
ISBN VARCHAR(255));

INSERT INTO BOOK (NAME, DESCRIPTION, AUTHOR, BOOK_TYPE, PRICE, ISBN) VALUES
('Harry Potter Series', 'Harry Potter is a series of seven fantasy novels written by British author J. K. Rowling. The novels chronicle the lives of a young wizard, Harry Potter, and his friends Hermione Granger and Ron Weasley, all of whom are students at Hogwarts School of Witchcraft and Wizardry.', 'J K Rowling', 'Fiction', 29.99, '9781408856772'),
('Angels and Demons', 'Angels & Demons is a 2000 bestselling mystery-thriller novel written by American author Dan Brown and published by Pocket Books and then by Corgi Books. The novel introduces the character Robert Langdon, who recurs as the protagonist of Browns subsequent novels.', 'Dan Brown', 'Crime Fiction', 39.99,  '9780743493468'),
('Thinking in Java', 'Thinking in Java is a book about the Java programming language, written by Bruce Eckel and first published in 1998. Prentice Hall published the 4th edition of the work in 2006. The book represents a print version of Eckel’s “Hands-on Java” seminar.', 'Bruce Eckel', 'Technology', 19.99,  '9780131872486'),
('Head First Design Patterns', '', 'Elisabeth Freeman, Kathy Sierra', 'Technology', 19.99,  '9780596007124'),
('A Brief History of Time', 'A Brief History of Time: From the Big Bang to Black Holes is a book on theoretical cosmology by English physicist Stephen Hawking. It was first published in 1988. Hawking wrote the book for readers who had no prior knowledge of physics and people who are interested in learning something new about interesting subjects. ', 'Stephen Hawking', 'Science', 49.99,  '9780553380168');
