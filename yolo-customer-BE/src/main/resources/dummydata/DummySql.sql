INSERT INTO yolo_customer.address
(id, house, street, area, zip_code, city, country, created_at, updated_at)
VALUES
(1, 'House 1', 'Street 1', 'Area 1', '12345', 'City 1', 'Country 1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'House 2', 'Street 2', 'Area 2', '23456', 'City 2', 'Country 2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'House 3', 'Street 3', 'Area 3', '34567', 'City 3', 'Country 3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO yolo_customer.`user`
(id, username, email, is_deleted, created_at, updated_at)
VALUES
(1, 'johndoe', 'john.doe@example.com', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'janedoe', 'jane.doe@example.com', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'guestuser', 'guest@example.com', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO yolo_customer.user_profile
(id, first_name, last_name, contact_number, user_id, currency_id, address_id, created_at, updated_at)
VALUES
(1, 'John', 'Doe', '+1234567890', 1, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Jane', 'Doe', '+0987654321', 2, 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Guest', 'User', '+1122334455', 3, 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO yolo_customer.idea
(id, title, description, code, idea_status_id, user_id, created_at, updated_at)
VALUES
(1, 'Healthy Salad', 'A nutritious and healthy salad recipe.', 'IDEA001', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Vegan Burger', 'A tasty vegan burger recipe.', 'IDEA002', 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Gluten-Free Pasta', 'A delicious gluten-free pasta recipe.', 'IDEA003', 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO yolo_customer.interest
(id, description, idea_id, created_at, updated_at)
VALUES
(1, 'High Protein', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Low Carb', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Dairy-Free', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO yolo_customer.dietary_restriction
(id, description, idea_id, created_at, updated_at)
VALUES
(1, 'Nut-Free', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Vegan', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Gluten-Free', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO yolo_customer.recipe
(id, name, description, serving_size, price, code, chef_code, chef_name, idea_id, currency_id, created_at, updated_at)
VALUES
(1, 'Quinoa Salad', 'A healthy quinoa salad with vegetables.', 2, 1500, 'REC001', 'CHE001', 'Alex', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Tofu Burger', 'A delicious tofu burger with vegan sauce.', 1, 1800, 'REC002', 'CHE002', 'Jacob', 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Zucchini Noodles', 'Gluten-free zucchini noodles with pesto sauce.', 2, 2000, 'REC003', 'CHE003', 'Max', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO yolo_customer.recipe_image
(id, url, recipe_id, created_at, updated_at)
VALUES
(1, 'https://example.com/images/quinoa_salad.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'https://example.com/images/tofu_burger.jpg', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'https://example.com/images/zucchini_noodles.jpg', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO yolo_customer.orders
(id, price, code, order_status_id, user_id, created_at, updated_at)
VALUES
(1, 1000, 'ORD001', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 2000, 'ORD002', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3000, 'ORD003', 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO yolo_customer.order_item
(id, quantity, order_id, recipe_id, price, created_at, updated_at)
VALUES
(1, 2, 1, 1, 500, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 1, 2, 2, 2000, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 3, 3, 3, 1500, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
