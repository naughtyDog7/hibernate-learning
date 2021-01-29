
# CREATE DOMAIN IF NOT EXISTS
# emailAddress AS VARCHAR
# CHECK (position('@', VALUE) > 1);
# CREATE DOMAIN IF NOT EXISTS
# username AS VARCHAR
# CHECK (substr(VALUE, 0, 5) != 'admin');