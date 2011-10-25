// -*- C++ -*-
//
// ======================================================================
//
//                           Brad T. Aagaard
//                        U.S. Geological Survey
//
// {LicenseText}
//
// ======================================================================
//

#include <portinfo>

#include "GeometryImporter.h" // implementation of class methods

#include "GeometryReader.h" // USES GeometryReader

#include <fstream> // USES std::ifstream

#include <sstream> // USES std::ostringstream
#include <stdexcept> // USES std::runtime_error

// ----------------------------------------------------------------------
// Default constructor
eqsimio::GeometryImporter::GeometryImporter(void):
  _filename("")
{ // constructor
} // constructor

// ----------------------------------------------------------------------
// Default destructor
eqsimio::GeometryImporter::~GeometryImporter(void)
{ // destructor
} // destructor

// ----------------------------------------------------------------------
// Import geometry. Geometry is passed back through the
// arguments.
void
eqsimio::GeometryImporter::import(int32_t* pMeshID,
				  uint32_t* pNumNodes,
				  uint32_t* pNumCoords,
				  float** ppCoords,
				  uint32_t* pNumSets,
				  uint32_t** ppSetSizes,
				  std::string** ppSetNames,
				  uint32_t* pConnSize,
				  uint32_t*** pppConns) const
{ // import
  // Open geometry file for reading
  std::ifstream fin(_filename.c_str());
  if (!fin.is_open() || !fin.good()) {
    std::ostringstream msg;
    msg << "Could not open EqSim geometry file '" << _filename
	<< "' for reading.";
    throw std::runtime_error(msg.str().c_str());
  } // if

  try {
    GeometryReader reader(fin);
    
    // Read header
    reader.readHeader(pMeshID);

    // Read coordinates of nodes
    reader.readCoords(pNumNodes, pNumCoords, ppCoords);

    // Read topology information (element sets)
    reader.readTopology(pNumSets, ppSetSizes, ppSetNames, pConnSize, pppConns);
  } catch (std::exception& err) {
    std::ostringstream msg;
    msg << "Error occurred while reading EqSim geometry file '" << _filename
	<< "'.\n"
	<< "Error message: " << err.what();
    throw std::runtime_error(msg.str().c_str());
  } catch (...) {
    std::ostringstream msg;
    msg << "Unknown error occurred while reading EqSim geometry file '"
	<< _filename << "'.\n";
    throw std::runtime_error(msg.str().c_str());
  } // catch

  fin.close();
} // import

// version
// $Id: GeometryImporter.cc 2363 2006-01-29 22:30:23Z brad $

// End of file 
