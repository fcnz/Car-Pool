# URI Breakdowns by HTTP method

1. "URL/car-pool" - Prefixes all other URI's
  * GET - Returns a list of all current Travelers in the database. (For admin/debug use only)
  * POST - Takes a dtoTraveler from XML and puts it in the database as a Domain Object with a new id as long as the email is unique.
2. "{id}" - @PathParam("id", long travelerID)
  * GET - Returns the traveler picked by it's ID as long as the traveler exists.
  * PUT - Updates a traveler's info, traveler picked by ID
3. "{id}/trips" - @PathParam("id", long travelerID)
  * GET - Returns all the trips for a given traveler, identified by id.
  * POST - Takes a dtoTrip from XML and creates a new trip for the given traveler, gives it an id and stores it in the database.
4. "trips/{time}-{start}-{end}" @PathParam("time", DateTime startTime, "start", GeoPosition startPos, "end", GeoPosition endPos)
  * GET - Searches for trips in the database that