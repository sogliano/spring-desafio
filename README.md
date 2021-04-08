# Desafío Spring



## Articles API

__Resumen__:
- Listar artículos: GET (/api/v1/articles).
- Realizar orden de compra: POST (/api/v1/articles/purchase-request).
- Visualizar carrito: GET (/api/v1/articles/cart).

### Obtener artículos:

__GET (/api/v1/articles)__

Se pueden recibir hasta dos parámetros (tres si uno de ellos es "order") con los cuales se realiza el filtro de búsqueda.
- Se valida:
    - que las keys de los parámetros sean los permitidos (productId,name,category,brand,price,freeShipping,prestige).
    - que los values para los valores numéricos (productId, price, prestige) no contengan letras.

### Realizar solicitud de compra:
__POST (/api/v1/articles/purchase-request)__

Se debe enviar una lista de artículos, conteniendo productId, nombre, marca y cantidad.

- Se valida:
    - que el articulo realmente exista en la BD (por su productId). (InvalidArticleException)
    - que la cantidad solicitada esté en Stock (consultamos a la BD). (AvailabilityException)

- Si todo está OK:
    - se resta del stock la cantidad solicitada de los productos.
    - se calcula el costo total en base a precios y cantidades.
    - se crea un Ticket (con artículos y costo total).
    - se agrega el Ticket al Cart.

Ejemplo de lo que se debe enviar:

```json
{
    "articles":
    [
        {
            "productId":1,
            "name":"Desmalezadora",
            "brand":"Makita",
            "quantity":1
        },
        {
            "productId":5,
            "name":"Zapatillas Deportivas",
            "brand":"Nike",
            "quantity":2
        }
    ]
}
```


### Visualizar carrito
__GET (/api/v1/articles/cart)__

- Se valida:
    - que existan elementos en el Cart. (EmptyCartException)

Si no está vacío, se muestra el listado de tickets que contiene el Cart, seguido del costo total acumulado de los tickets.

## Clients API

__Resumen__:
- Listar clientes: GET (/api/v1/clients).
- Realizar orden de compra: POST (/api/v1/clients/add-client).

### Obtener clientes:
__GET (/api/v1/clients)__

Se pueden recibir hasta dos parámetros con los cuales se realiza el filtro de búsqueda.
Si envia mas de dos parámetros se tira ParametersQuantityException.
- Se valida:
    - que las keys de los parámetros sean los permitidos.(name, email, cellphone, province). (InvalidParameterException)
    - que los values name, email y province sean válidos (cada uno tiene una validación distinta). (InvalidClientParamsException)

### Agregar cliente:
__POST (/api/v1/clients/add-client)__

Se debe enviar los datos del cliente a ingresar.
- Se valida
    - que no exista el cliente previamente en la base de datos (por el email) (ClientExistsException).
    - que no falten parámetros. (InvalidParameterException)