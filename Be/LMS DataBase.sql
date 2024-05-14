CREATE DATABASE if not exists LmsProject;
USE LmsProject;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255),
    fullname VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    google_id VARCHAR(50),
    date_of_birth DATE NOT NULL,
    picture VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    subject_id VARCHAR(255),
    ethnicity_id INT NOT NULL,
    role ENUM('Admin', 'Student', 'Teacher')
);
CREATE TABLE ethnicities (
    ethnicity_id INT AUTO_INCREMENT primary key,
    name VARCHAR(50)
);
CREATE TABLE relatives (
    relative_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    job VARCHAR(50),
    dob DATE,
    address VARCHAR(50),
    FOREIGN KEY (user_id)
        REFERENCES users (user_id)
);

CREATE TABLE class (
    class_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(50),
    quantity INT NOT NULL,
    teacher_id INT NOT NULL,
    FOREIGN KEY (teacher_id)
        REFERENCES users (user_id)
);
CREATE TABLE departments (
    department_id VARCHAR(20) PRIMARY KEY,
    department_name VARCHAR(50)
);

CREATE TABLE subjects (
    subject_id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255),
    department_id VARCHAR(20),
    FOREIGN KEY (department_id)
        REFERENCES departments(department_id)
);
CREATE TABLE grades (
    grade_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    subject_id VARCHAR(255),
    weight_1 FLOAT,
    weight_2 FLOAT,
    weight_3 FLOAT,
    average_grade FLOAT,
    semester INT,
    FOREIGN KEY (student_id)
        REFERENCES users (user_id),
    FOREIGN KEY (subject_id)
        REFERENCES subjects (subject_id)
);

CREATE TABLE academic_results (
    academic_result_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    conduct ENUM('Very Good', 'Good', 'Average', 'Weak'),
    average_first_semester FLOAT,
    average_second_semester FLOAT,
    final_grade FLOAT,
    FOREIGN KEY (student_id)
        REFERENCES users (user_id)
);

CREATE TABLE assignments (
    assignment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    assignment_name VARCHAR(255),
    description TEXT,
    subject_id VARCHAR(255),
    start_date DATETIME,
    end_date DATETIME,
    file_path VARCHAR(255),
    FOREIGN KEY (subject_id)
        REFERENCES subjects (subject_id)
);

CREATE TABLE submissions (
    submission_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    assignment_id BIGINT,
    student_id INT,
    grade FLOAT,
    FOREIGN KEY (assignment_id)
        REFERENCES assignments (assignment_id),
    FOREIGN KEY (student_id)
        REFERENCES users (user_id)
);

CREATE TABLE allocations (
    allocation_id INT PRIMARY KEY,
    subject_id VARCHAR(255),
    teacher_id INT,
    FOREIGN KEY (teacher_id)
        REFERENCES users (user_id),
    FOREIGN KEY (subject_id)
        REFERENCES subjects (subject_id)
);

CREATE TABLE fees (
    fee_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description LONGTEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    amount INT NOT NULL,
    completed BOOLEAN DEFAULT FALSE
);

CREATE TABLE transactions (
	transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    fee_id INT NOT NULL,
    student_id INT NOT NULL,
    amount INT NOT NULL,
    transaction_date Date NOT NULL,

	FOREIGN KEY (fee_id)
	    REFERENCES fees (fee_id),
        
    FOREIGN KEY (student_id)
	    REFERENCES users (user_id)    
);



ALTER TABLE users
ADD CONSTRAINT FK_ethnicity_id FOREIGN KEY (ethnicity_id) REFERENCES ethnicities(ethnicity_id),
ADD CONSTRAINT FK_subject_id FOREIGN KEY (subject_id) REFERENCES subjects(subject_id);


ALTER TABLE grades
ADD CONSTRAINT FK_grade_student_id FOREIGN KEY (student_id) REFERENCES users(user_id),
ADD CONSTRAINT FK_grade_subjects_id FOREIGN KEY (subject_id) REFERENCES subjects(subject_id);

ALTER TABLE academic_results
ADD CONSTRAINT FK_academic_result_student_id FOREIGN KEY (student_id) REFERENCES users(user_id);

ALTER TABLE assignments
ADD CONSTRAINT FK_assignment_subject_id FOREIGN KEY (subject_id) REFERENCES subjects(subject_id);

ALTER TABLE submissions
ADD CONSTRAINT FK_submission_assignment_id FOREIGN KEY (assignment_id) REFERENCES assignments(assignment_id),
ADD CONSTRAINT FK_submission_student_id FOREIGN KEY (student_id) REFERENCES users(user_id);

Insert INTO ethnicities (ethnicity_id, name)
values (1, 'Kinh');

INSERT INTO departments (department_id, department_name) VALUES
('TT', 'Toán-Tin'),
('LC', 'Lý-Công Nghệ'),
('HS', 'Hoá-Sinh'),
('VS','Văn-Sử'),
('ĐG', 'Địa-GDCD'),
('TA', 'Anh'),
('TD', 'Thể dục');

INSERT INTO subjects (subject_id, title, department_id)
VALUES
    ('MA', 'Toán', 'TT'),
    ('LY', 'Lý', 'LC'),
    ('HO', 'Hóa', 'HS'),
    ('SI','Sinh','HS'),
    ('VA', 'Văn', 'VS'),
    ('SU', 'Sử', 'VS'),
    ('DI', 'Địa', 'ĐG'),
    ('AV', 'Tiếng Anh', 'TA'),
    ('GD', 'GDCD', 'ĐG'),
    ('TI', 'Tin Học', 'TT'),
    ('PE', 'Thể Dục', 'TD');

INSERT INTO users (fullname, phone_number, email, password,  date_of_birth, address, picture, google_id, `role`, ethnicity_id, subject_id) 
VALUES 
 ('Nguyễn Trần Tấn Phát', '0987654321', 'phatntt1923@gmail.com', null, '2003-10-19', '32 Thuỷ Lợi, Q9, Tp HCM', 'https://lh3.googleusercontent.com/a/ACg8ocK3mbc_XIS8aOd3-QIHVimre8l5rO2y1a16rbzmHsGgm9-OPA=s96-c', '109715265001449318298', 'Student', 1, null),
 ('Lê Hoàng Ngọc Thời', '0987654321', 'lhnt@gmail.com', null, '2003-10-19', '32 Thuỷ Lợi, Q9, Tp HCM', 'https://lh3.googleusercontent.com/a/ACg8ocK3mbc_XIS8aOd3-QIHVimre8l5rO2y1a16rbzmHsGgm9-OPA=s96-c', '109715265001449318298', 'Student', 1, null),
  ('Nguyễn Hoàng Phúc', '0987654321', 'phucnhse173197@fpt.edu.vn', null, '2003-10-19', '32 Thuỷ Lợi, Q9, Tp HCM', 'https://lh3.googleusercontent.com/a/ACg8ocK3mbc_XIS8aOd3-QIHVimre8l5rO2y1a16rbzmHsGgm9-OPA=s96-c', '109715265001449318298', 'Student', 1, null),
  
  
 ('Đặng Thị Vĩnh Thuỵ', '0987654321', 'thuydang@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'HO'),
  ('Nguyễn Tú Trinh', '0987654321', 'trinhtu@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'LY'),
    ('Lê Thị Lệ Hằng', '0987654321', 'hangle@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'AV'),
      ('Đoàn Thị Hương', '0987654321', 'huongdoan@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'SI'),
        ('Nguyễn Thị Thanh Thuỷ', '0987654321', 'trinhtu@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'SU'),
          ('Giáo viên GDCD', '0987654321', 'gvgdcd@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'GD'),
            ('Giáo viên Toán', '0987654321', 'gvtoan@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'MA'),
              ('Giáo viên Văn', '0987654321', 'gvvan@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'VA'),
              ('Giáo viên Thể Dục', '0987654321', 'gvtd@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'PE'),
              ('Giáo viên Tin', '0987654321', 'gvtin@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'TI'),
               ('Giáo viên Địa', '0987654321', 'gvdia@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'DI'),
                ('Giáo viên GDCD', '0987654321', 'gvgdcd@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'GD'),
            ('Giáo viên Toán 2', '0987654321', 'gvtoan2@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'MA'),
              ('Giáo viên Văn 2', '0987654321', 'gvvan2@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'VA'),
              ('Giáo viên Thể Dục 2', '0987654321', 'gvtd2@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'PE'),
              ('Giáo viên Tin 2', '0987654321', 'gvtin2@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'TI'),
               ('Giáo viên Địa 2', '0987654321', 'gvdia2@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Teacher', 1, 'DI'),
              
              
 ('Hồ Trung Cang', '0987654321', 'cangho@gmail.com','$2a$04$NpdGyhjIAcqTz9HTeZioJukdxkAD9TKpu3td.i2ncpbcaR9OM.evO', '2003-10-19', 'huyện Ngọc Hồi, Tp HCM', 'https://www.topcv.vn/images/avatar-default.jpg', '', 'Admin', 1,  null);


INSERT INTO class (class_id, `name`, quantity, teacher_id) VALUES 
('12A1-2018-2021', '12A1', 30, 4),
('12A2-2018-2021', '12A2', 30, 5),
('12A3-2018-2021', '12A3', 30, 6),
('12A4-2018-2021', '12A4', 30, 7),
('12A5-2018-2021', '12A5', 30, 8),
('12A6-2018-2021', '12A6', 30, 9),
('12A7-2018-2021', '12A7', 30, 10),

('11B1-2018-2021', '11A1', 30, 11),
('11B2-2018-2021', '11A2', 30, 12),
('11B3-2018-2021', '11A3', 30, 13),
('11B4-2018-2021', '11A4', 30, 14),
('11B5-2018-2021', '11A5', 30, 15),
('11B6-2018-2021', '11A6', 30, 16),
('11B7-2018-2021', '11A7', 30, 17),

('10C1-2018-2021', '10C1', 30, 18),
('10C2-2018-2021', '10C2', 30, 19),
('10C3-2018-2021', '10C3', 30, 20),
('10C4-2018-2021', '10C4', 30, 5),
('10C5-2018-2021', '10C5', 30, 6),
('10C6-2018-2021', '10C6', 30, 7),
('10C7-2018-2021', '10C7', 30, 8);






