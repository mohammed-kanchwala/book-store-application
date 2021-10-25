CREATE TABLE BOOK (ID INT IDENTITY PRIMARY KEY,
NAME VARCHAR(150),
DESCRIPTION VARCHAR(500),
AUTHOR VARCHAR(255),
BOOK_TYPE VARCHAR(255),
PRICE DOUBLE,
ISBN VARCHAR(255));

INSERT INTO BOOK (NAME, DESCRIPTION, AUTHOR, BOOK_TYPE, PRICE, ISBN) VALUES
('Harry Potter Series', 'Harry Potter is a series of seven fantasy novels written by British author J. K. Rowling. The novels chronicle the lives of a young wizard, Harry Potter, and his friends Hermione Granger and Ron Weasley, all of whom are students at Hogwarts School of Witchcraft and Wizardry.', 'J K Rowling', 'Fiction', 29.99, '9781408856772'),
('Angels and Demons', 'Angels & Demons is a 2000 bestselling mystery-thriller novel written by American author Dan Brown and published by Pocket Books and then by Corgi Books. The novel introduces the character Robert Langdon, who recurs as the protagonist of Browns subsequent novels.', 'Dan Brown', 'Crime Fiction', 39.99,  '9780743493468'),
('Thinking in Java', 'Thinking in Java is a book about the Java programming language, written by Bruce Eckel and first published in 1998. Prentice Hall published the 4th edition of the work in 2006. The book represents a print version of Eckel’s “Hands-on Java” seminar.', 'Bruce Eckel', 'Technology', 19.99,  '9780131872486'),
('Spring Boot in Action', 'A developer-focused guide to writing applications using Spring Boot. You''ll learn how to bypass the tedious configuration steps so that you can concentrate on your application''s behavior.', 'Craig Walls', 'Technology', 14.99,  '9781617292545'),
('Head First Design Patterns', 'You know you don''t want to reinvent the wheel, so you look to Design Patterns: the lessons learned by those who''ve faced the same software design problems', 'Elisabeth Freeman and Kathy Sierra', 'Technology', 24.99,  '9780596007124'),
('Clean Code', 'Even bad code can function. But if code isn''t clean, it can bring a development organization to its knees. Every year, countless hours and significant resources are lost because of poorly written code.', 'Robert Cecil Martin', 'Technology', 24.99,  '9780132350884'),
('A Brief History of Time', 'A Brief History of Time: From the Big Bang to Black Holes is a book on theoretical cosmology by English physicist Stephen Hawking. It was first published in 1988. Hawking wrote the book for readers who had no prior knowledge of physics and people who are interested in learning something new about interesting subjects.', 'Stephen Hawking', 'Science', 49.99,  '9780553380168'),
('A Short History of Nearly Everything', 'A Short History of Nearly Everything by American-British author Bill Bryson is a popular science book that explains some areas of science, using easily accessible language that appeals more to the general public than many other books dedicated to the subject.', 'Bill Bryson', 'Science', 59.99,  '9781784161859'),
('The Body: A Guide for Occupants', 'Bill Bryson, bestselling author of A Short History of Nearly Everything, takes us on a head-to-toe tour of the marvel that is the human body. As compulsively readable as it is comprehensive, this is Bryson at his very best, a must-read owner''s manual for everybody', 'Bill Bryson', 'Science', 44.99,  '9780552779913'),
('What If?: Serious Scientific Answers to Absurd Hypothetical Questions', 'What If?: Serious Scientific Answers to Absurd Hypothetical Questions is a non-fiction book by Randall Munroe in which the author answers hypothetical science questions sent to him by readers of his webcomic.', 'Randall Munroe', 'Science', 48.99,  '9780544456860');
