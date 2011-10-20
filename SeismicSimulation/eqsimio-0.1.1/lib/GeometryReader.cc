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

#include "GeometryReader.h" // implementation of class methods

#include "StreamTranslator.h" // USES StreamTranslator

#include <iostream> // USES std::istream
#include <assert.h> // USES assert()
#include <stdexcept> // USES std::runtime_error

// ----------------------------------------------------------------------
// Default constructor
eqsimio::GeometryReader::GeometryReader(std::istream& fin):
  _fin(fin),
  _pTranslator(new StreamTranslator)
{ // constructor
} // constructor

// ----------------------------------------------------------------------
// Default destructor
eqsimio::GeometryReader::~GeometryReader(void)
{ // destructor
  delete _pTranslator; _pTranslator = 0;
} // destructor

// ----------------------------------------------------------------------
// Read file header.
void
eqsimio::GeometryReader::readHeader(int32_t* pMeshID)
{ // readHeader
  const char* magicheader = "#EQSIM.mesh  "; // Expected magic header
  const int formatVersion = 3; // Expected format version
  const char* bigendianstr = "BIG ENDIAN"; // String for big endian
  const char* lilendianstr = "LIL ENDIAN"; // String for little endian

  assert(_fin.good());
  assert(0 != _pTranslator);

  // Read magic header
  uint32_t len = strlen(magicheader);
  assert(len > 0);
  char* cbuffer = new char[len+1];
  _fin.read(cbuffer, len*sizeof(*cbuffer));
  cbuffer[len] = '\0';

  if (strcmp(magicheader, cbuffer) != 0)
    throw std::runtime_error("Mismatch in magic header.");
  delete[] cbuffer; cbuffer = 0;

  // Read endian type for data in file
  len = strlen(bigendianstr);
  cbuffer = new char[len+1];
  _fin.read(cbuffer, len*sizeof(*cbuffer));
  cbuffer[len] = '\0';
  if (strcasecmp(bigendianstr, cbuffer) == 0)
    _pTranslator->streamEndian(StreamTranslator::BIGENDIAN);
  else if (strcasecmp(lilendianstr, cbuffer) == 0)
    _pTranslator->streamEndian(StreamTranslator::LITTLEENDIAN);
  else
    throw std::runtime_error("Could not parse endian string into endian type.");
  delete[] cbuffer; cbuffer = 0;

  // Read format version
  uint32_t version;
  _pTranslator->translate(_fin, (char*) &version, 1, sizeof(version));
  if (formatVersion != version)
    throw std::runtime_error("Could not read format version.");

  // Read mesh ID
  int32_t meshID;
  _pTranslator->translate(_fin, (char*) &meshID, 1, sizeof(meshID));
  if (!_fin.good())
    throw std::runtime_error("Could not read mesh ID.");
  
  // Read origin
  len = 0;
  _pTranslator->translate(_fin, (char*) &len, 1, sizeof(len));
  cbuffer = (len > 0) ? new char[len] : 0;
  _fin.read(cbuffer, len*sizeof(*cbuffer));
  delete[] cbuffer; cbuffer = 0;
  if (!_fin.good())
    throw std::runtime_error("Could not read UTM zone in origin.");

  len = 3;
  float* pXYZ = (len > 0) ? new float[len] : 0;
  _pTranslator->translate(_fin, (char*) pXYZ, len, sizeof(*pXYZ));
  if (!_fin.good())
    throw std::runtime_error("Could not read origin coordinates.");

  // Set arguments
  if (0 != pMeshID)
    *pMeshID = meshID;
} // readHeader
  
// ----------------------------------------------------------------------
// Read coordinates of nodes.
void
eqsimio::GeometryReader::readCoords(uint32_t* pNumNodes,
				    uint32_t* pNumCoords,
				    float** ppCoords)
{ // readCoords
  assert(_fin.good());
  assert(0 != _pTranslator);

  // Read number of nodes
  uint32_t numNodes;
  _pTranslator->translate(_fin, (char*) &numNodes, 1, sizeof(numNodes));

  const int numCoords = 3;
  
  // Read coordinates of nodes
  const uint32_t size = numNodes*numCoords;
  float* pCoords = (size > 0) ? new float[size] : 0;
  _pTranslator->translate(_fin, (char*) pCoords, size, sizeof(*pCoords));

  // Set arguments
  if (0 != pNumNodes)
    *pNumNodes = numNodes;
  if (0 != pNumCoords)
    *pNumCoords = numCoords;
  if (0 != ppCoords)
    *ppCoords = pCoords;
  else {
    delete[] pCoords; pCoords = 0;
  } // else
} // readCoords
  
// ----------------------------------------------------------------------
// Read topology (element sets).
void
eqsimio::GeometryReader::readTopology(uint32_t* pNumSets,
				      uint32_t** ppSetSizes,
				      std::string** ppSetNames,
				      uint32_t* pConnSize,
				      uint32_t*** pppConns)
{ // readTopology
  assert(_fin.good());
  assert(0 != _pTranslator);

  // Read number of element sets
  uint32_t numSets;
  _pTranslator->translate(_fin, (char*) &numSets, 1, sizeof(numSets));
  if (!_fin.good())
    throw std::runtime_error("Could not read number of element sets.");

  // Read connectivity size
  uint32_t connSize;
  _pTranslator->translate(_fin, (char*) &connSize, 1, sizeof(connSize));
  if (!_fin.good())
    throw std::runtime_error("Could not read connectivity sizes.");

  uint32_t* pSetSizes = (numSets > 0) ? new uint32_t[numSets] : 0;
  std::string* pSetNames = (numSets > 0) ? new std::string[numSets] : 0;
  uint32_t** ppConns = (numSets > 0) ? new uint32_t*[numSets] : 0;

  for (uint32_t iSet=0; iSet < numSets; ++iSet) {
    // Read set name
    uint32_t len = 0;
    _pTranslator->translate(_fin, (char*) &len, 1, sizeof(len));
    char* cbuffer = (len > 0) ? new char[len+1] : 0;
    _fin.read(cbuffer, len*sizeof(*cbuffer));
    cbuffer[len] = '\0';
    pSetNames[iSet] = cbuffer;
    delete[] cbuffer; cbuffer = 0;
    if (!_fin.good())
      throw std::runtime_error("Could not read set name.");

    // Read region tag
    uint32_t tag;
    _pTranslator->translate(_fin, (char*) &tag, 1, sizeof(tag));
    if (!_fin.good())
      throw std::runtime_error("Could not read region tag.");

    // Read number of elements in set
    uint32_t setSize;
    _pTranslator->translate(_fin, (char*) &setSize, 1, sizeof(setSize));
    pSetSizes[iSet] = setSize;
    if (!_fin.good())
      throw std::runtime_error("Could not read number of elements in set.");

    // Read element connectivity arrays
    const uint32_t size = setSize * connSize;
    ppConns[iSet] = (size > 0) ? new uint32_t[size] : 0;
    _pTranslator->translate(_fin, (char*) ppConns[iSet], size, 
			    sizeof(**ppConns));
    if (!_fin.good())
      throw std::runtime_error("Could not read connectivity arrays.");
  } // for

  // Set arguments
  if (0 != pNumSets)
    *pNumSets = numSets;
  if (0 != ppSetSizes)
    *ppSetSizes = pSetSizes;
  else {
    delete[] pSetSizes; pSetSizes = 0;
  } // else
  if (0 != ppSetNames)
    *ppSetNames = pSetNames;
  else {
    delete[] pSetNames; pSetNames = 0;
  } // else
  if (0 != pConnSize)
    *pConnSize = connSize;
  if (0 != pppConns)
    *pppConns = ppConns;
  else {
    for (int i=0; i < numSets; ++i) {
      delete ppConns[i]; ppConns[i] = 0;
    } // for
    delete[] ppConns; ppConns = 0;
  } // else
} // readTopology

// version
// $Id: GeometryReader.cc 2364 2006-01-29 22:36:38Z brad $

// End of file 
