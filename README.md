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
    "creationTimestamp": "2021-04-14T03:16:27.031978",
    "tiers": [
        {
            "id": 1,
            "title": "tier1",
            "description": "tier1 description",
            "price": 5.50
        },
        {
            "id": 2,
            "title": "tier2",
            "description": "tier2 description",
            "price": 7.50
        }
    ],
    "tags": [
        {
            "name": "java"
        },
        {
            "name": "backend"
        }
    ]
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
    "tags": ["java", "backend"],
    "tiers": [
        { 
            "title": "tier1",
            "description": "tier1 description",
            "price": 5.50
        },
        { 
            "title": "tier2",
            "description": "tier2 description",
            "price": 7.50
        }
    ]
}
```

Response example: `1` (ID of new offer).


### Deleting offer

URL: `localhost:8080/offers/<offer_id>` (DELETE method)

Response example: `1` (ID of deleted offer).

Please note that deleted offers are not removed from the database (only a flag is being set).


### Updating offer

URL: `localhost:8080/offers/<offer_id>` (POST method)

Request body example (same as posting new offer):
```json
{
    "title": "Example offer after editing",
    "description": "Example description",
    "ownerId": 0,
    "tags": ["c++", "embedded"],
    "tiers": [
        { 
            "title": "tier2",
            "description": "tier1 description",
            "price": 4.50
        },
        { 
            "title": "tier3",
            "description": "tier2 description",
            "price": 5.50
        }
    ]
}
```

Response example: `2` (ID of offer after update).

Please note: it is important to understand that this deletes (marks as archived) the old offer 
and creates the new one (to preserve old version of offer). ID of new offer is returned, and details
of this new offer should be fetched.


### Getting offers (filtering, sorting and pagination)

URL: `localhost:8080/offers?<params>`

For example: `localhost:8080/offers?pageLimit=8&pageOffset=0&orderBy=desc:creation_timestamp&minimalPriceFilter=lt:6`

Sorting: `orderBy=<direction>:<field>`, where `direction = (asc|desc)` and `field = (title|creation_timestamp|lowest_price)` 
(for now).

Pagination: `pageLimit=<value>&pageOffer=<value>` - limit is 20 by default and offset is 0 by default

Filtering: `<property>=<condition>:<value>`, for example `minimalPriceFilter=lt:6` (will be renamed).

Filtering by tags: `tags=<tag_list>`, for example `tags=java,backend`.

Response example:
```json
{
    "totalNumberOfOffers": 4,
    "offers": [
        {
            "id": 6,
            "name": "Example offer after editing",
            "minimalPrice": 4.50
        },
        {
            "id": 5,
            "name": "Example offer 2",
            "minimalPrice": 5.50
        },
        {
            "id": 4,
            "name": "Example offer 2",
            "minimalPrice": 5.50
        },
        {
            "id": 3,
            "name": "Example offer 2",
            "minimalPrice": 5.50
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


# java-repository-template

### How to finish setting up java repository

* Register repository to [codecov.io](https://app.codecov.io/). *Remember to add proper secret into your repository configuration*
* Create badge for code coverage. Copy from:`https://app.codecov.io/gh/<user>/<repository>>/settings/badge`
* Create badge for CI action 
* Template is prepared for Maven based project. Add JaCoCo to your project to properly generate coverage report.
```
![CI/CD](https://github.com/<user>/<repository>/actions/workflows/ci.yml/badge.svg)
```
