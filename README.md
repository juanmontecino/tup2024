# tup2024

Para correr la app: mvn spring-boot:run

Acceso a la API a través de Swagger: http://localhost:8080/swagger-ui/index.html

Tecnologías
Java
Springboot
Junit + Mockito
Swagger

POST /api/cliente
{
    "nombre": "string",
    "apellido": "string",
    "dni": long,
    "fechaNacimiento": "string", // YYYY-MM-DD
    "tipoPersona": "string", // "F" o "J" -> FISICA o JURIDICA
    "banco": "string"
}

GET /api/cliente/{dni} // Obtener el cliente pasado por dni

POST /api/cuenta
{
    "dniTitular": 0,
    "tipoCuenta": "string", // "C" o "A" -> Cuenta Corriente o Caja de Ahorro
    "moneda": "string" // "P" o "D" -> PESOS o DOLARES
}

GET /api/cuenta/{NumeroCuenta} // Obtener la cuenta pasada por id

POST /api/prestamo
{
"numeroCliente": long,
"plazoMeses": int,
"montoPrestamo": long,
"moneda": "string" // "P" o "D" -> PESOS o DOLARES
}

GET /api/prestamo/{Dni} // Obtener todos los prestamos de un cliente

POST /api/prestamo/{idPrestamo} //Pagar cuota del prestamo pasado por id

