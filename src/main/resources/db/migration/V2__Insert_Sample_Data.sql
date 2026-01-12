-- Insert Sample Users (Password: password123)
INSERT INTO users (username, email, password, full_name, role, enabled) VALUES
('admin', 'admin@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Admin User', 'ADMIN', true),
('john_premium', 'john@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'John Doe Premium', 'PREMIUM_USER', true),
('jane_doe', 'jane@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Jane Doe', 'USER', true);

-- Insert Sample Products
INSERT INTO products (name, description, price, stock_quantity, category, sku) VALUES
('Laptop Pro 15', 'High-performance laptop with 16GB RAM', 1299.99, 50, 'Electronics', 'LAP-PRO-15'),
('Wireless Mouse', 'Ergonomic wireless mouse with USB receiver', 29.99, 200, 'Electronics', 'MOU-WIR-01'),
('Mechanical Keyboard', 'RGB mechanical gaming keyboard', 89.99, 100, 'Electronics', 'KEY-MEC-RGB'),
('USB-C Hub', '7-in-1 USB-C hub with multiple ports', 49.99, 150, 'Electronics', 'HUB-USBC-7'),
('Laptop Stand', 'Adjustable aluminum laptop stand', 39.99, 80, 'Accessories', 'STD-LAP-ALU'),
('Webcam HD', '1080p HD webcam with microphone', 79.99, 60, 'Electronics', 'CAM-HD-1080'),
('Headphones Pro', 'Noise-cancelling wireless headphones', 199.99, 40, 'Electronics', 'HEAD-PRO-NC'),
('Monitor 27"', '27-inch 4K UHD monitor', 399.99, 30, 'Electronics', 'MON-27-4K'),
('Desk Lamp LED', 'Adjustable LED desk lamp with USB charging', 34.99, 120, 'Accessories', 'LMP-LED-USB'),
('External SSD 1TB', '1TB portable external SSD', 149.99, 70, 'Storage', 'SSD-EXT-1TB');
