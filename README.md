# Offers microservice

![CI/CD](https://github.com/ProgramowanieZespoloweIS2021/offers-service/actions/workflows/ci.yml/badge.svg)

[![codecov](https://codecov.io/gh/ProgramowanieZespoloweIS2021/offers-service/branch/main/graph/badge.svg?token=ODDINBT1E9)](https://codecov.io/gh/ProgramowanieZespoloweIS2021/offers-service)

## Overview
TODO: prepare

## Running
It is easiest to run this using Docker. Run following commands to create a docker network and containers with 
both database and the app:

```shell script
docker network create eszop

docker run --network eszop \
  -d -p 5432:5432 \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin \
  --name psql_offers_eszop \
  postgres

docker run --network eszop \
  -d -p 8080:8080 \
  -e POSTGRES_HOST=psql_offers_eszop \
  -e POSTGRES_PORT=5432 \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin \
  --name offers_eszop \
  arokasprz100/offers:latest

```

App will be available on `localhost:8080`



## For developers
Following actions are currently supported:
 * post new offer
 * get offer details
 * get offers (filtering, sorting and pagination)
 * update offer
 * delete offer
 * get all tags


### Getting offer details

URL: `localhost:8080/offers/<offer_id>` (GET method)

Response example:
```json
{
    "id": 1,
    "ownerId": 0,
    "title": "Example offer 2",
    "description": "Example description",
    "creationTimestamp": "2021-04-16T21:05:36.904969",
    "tiers": [
        {
            "id": 1,
            "title": "tier1",
            "description": "tier1 description",
            "price": 5.50,
            "deliveryTime": 2
        },
        {
            "id": 2,
            "title": "tier2",
            "description": "tier2 description",
            "price": 7.50,
            "deliveryTime": 3
        }
    ],
    "tags": [
        {
            "name": "cpp"
        },
        {
            "name": "java"
        },
        {
            "name": "backend"
        }
    ],
    "thumbnails": [
        {
            "id": 1,
            "url": "https://upload.wikimedia.org/wikipedia/commons/8/87/W3sDesign_Builder_Design_Pattern_UML.jpg"
        }
    ],
    "archived": false
}
```


### Posting new offer

URL: `localhost:8080/offers` (POST method)

Request body example:
```json
{
    "title": "Example offer 2",
    "description": "Example description",
    "ownerId": 0,
    "tags": ["cpp", "java", "backend"],
    "thumbnails": ["https://upload.wikimedia.org/wikipedia/commons/8/87/W3sDesign_Builder_Design_Pattern_UML.jpg"],
    "tiers": [
        { 
            "title": "tier1",
            "description": "tier1 description",
            "price": 5.50,
            "deliveryTime": 2
        },
        { 
            "title": "tier2",
            "description": "tier2 description",
            "price": 7.50,
            "deliveryTime": 3
        }
    ]
}
```

Response example: `1` (ID of new offer).

Example error response:
```json
{
    "tiers[0].price": "Tier price can not be negative.",
    "tiers[0].title": "Tier title is mandatory and should not be empty.",
    "ownerId": "Owner ID is mandatory."
}
```


### Deleting offer

URL: `localhost:8080/offers/<offer_id>` (DELETE method)

Response example: `1` (ID of deleted offer).

Please note that deleted offers are not removed from the database (only a flag is being set).


### Updating offer

URL: `localhost:8080/offers/<offer_id>` (POST method)

Request body, response and errors are the same as for adding an offer.

Please note: it is important to understand that this deletes (marks as archived) the old offer 
and creates the new one (to preserve old version of offer). ID of new offer is returned, and details
of this new offer should be fetched.


### Getting offers (filtering, sorting and pagination)

URL: `localhost:8080/offers?<params>`

For example: `localhost:8080/offers?limit=8&offset=0&order_by=desc:creation_timestamp&min_price=lt:6`

Sorting: `order_by=<direction>:<field>`, where `direction = (asc|desc)` and `field = (title|creation_timestamp|lowest_price)` 
(for now).

Pagination: `limit=<value>&offset=<value>` - limit is 20 by default and offset is 0 by default

Filtering: `<property>=<condition>:<value>`, for example `min_price=lt:6` (will be renamed).

Filtering by tags: `tags=<tag_list>`, for example `tags=java,backend`.

***Note for frontend developers:*** what exactly should be returned here? What sorting and filtering should be implemented?

Response example:
```json
{
    "totalNumberOfOffers": 4,
    "offers": [
        {
            "id": 1,
            "ownerId": 0,
            "name": "Example offer 2",
            "minimalPrice": 5.50,
            "tags": [
                {
                    "name": "cpp"
                },
                {
                    "name": "java"
                },
                {
                    "name": "backend"
                }
            ],
            "thumbnails": [
                {
                    "id": 1,
                    "url": "https://upload.wikimedia.org/wikipedia/commons/8/87/W3sDesign_Builder_Design_Pattern_UML.jpg"
                }
            ]
        },
        {
            "id": 2,
            "ownerId": 0,
            "name": "Example offer 2",
            "minimalPrice": 5.50,
            "tags": [
                {
                    "name": "cpp"
                },
                {
                    "name": "java"
                },
                {
                    "name": "backend"
                }
            ],
            "thumbnails": [
                {
                    "id": 2,
                    "url": "https://upload.wikimedia.org/wikipedia/commons/8/87/W3sDesign_Builder_Design_Pattern_UML.jpg"
                }
            ]
        },
        {
            "id": 3,
            "ownerId": 0,
            "name": "Example offer 2",
            "minimalPrice": 5.50,
            "tags": [
                {
                    "name": "cpp"
                },
                {
                    "name": "java"
                },
                {
                    "name": "backend"
                }
            ],
            "thumbnails": [
                {
                    "id": 3,
                    "url": "https://upload.wikimedia.org/wikipedia/commons/8/87/W3sDesign_Builder_Design_Pattern_UML.jpg"
                }
            ]
        },
        {
            "id": 4,
            "ownerId": 0,
            "name": "Example offer 2",
            "minimalPrice": 5.50,
            "tags": [
                {
                    "name": "cpp"
                },
                {
                    "name": "java"
                },
                {
                    "name": "backend"
                }
            ],
            "thumbnails": [
                {
                    "id": 4,
                    "url": "https://upload.wikimedia.org/wikipedia/commons/8/87/W3sDesign_Builder_Design_Pattern_UML.jpg"
                }
            ]
        }
    ]
}
```


### Getting all tags

ULR: `localhost:8080/offers/tags`

Example response:
```json
[
    {
        "name": "java"
    },
    {
        "name": "javascript"
    },
    {
        "name": "c#"
    },
    {
        "name": "react"
    }
]
```
