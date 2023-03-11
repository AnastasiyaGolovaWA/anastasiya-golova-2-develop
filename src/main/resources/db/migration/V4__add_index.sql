ALTER TABLE news ADD COLUMN document_tsvector tsvector;
UPDATE news SET document_tsvector = to_tsvector('russian', tittle || ' ' || description || ' ' || pub_date);


