-- 1. Crear una lista por defecto si todavía no existe
INSERT INTO listas (nombre)
SELECT 'General'
WHERE NOT EXISTS (
    SELECT 1
    FROM listas
    WHERE nombre = 'General'
);

-- 2. Asignar la lista por defecto a los favoritos sin lista
UPDATE favoritos
SET lista_id = (
    SELECT id
    FROM listas
    WHERE nombre = 'General'
)
WHERE lista_id IS NULL;

-- 3. Hacer que la lista sea obligatoria
ALTER TABLE favoritos
ALTER COLUMN lista_id SET NOT NULL;