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

#include "DataReader.h" // implementation of class methods

#include "StreamTranslator.h" // USES StreamTranslator

#include <iostream> // USES std::istream
#include <assert.h> // USES assert()
#include <stdexcept> // USES std::runtime_error

// ----------------------------------------------------------------------
// Default constructor
eqsimio::DataReader::DataReader(const char* filename) :
  _fin(filename),
  _pTranslator(new StreamTranslator),
  _sizeNodeVals(0),
  _numSets(0),
  _pSizeElemVals(0)
{ // constructor
  if (!_fin.is_open() | !_fin.good())
    throw std::runtime_error("Could not open file.");
} // constructor

// ----------------------------------------------------------------------
// Default destructor
eqsimio::DataReader::~DataReader(void)
{ // destructor
  _fin.close();
  delete _pTranslator; _pTranslator = 0;
  _sizeNodeVals = 0;
  _numSets = 0;
  delete[] _pSizeElemVals; _pSizeElemVals = 0;
} // destructor

// ----------------------------------------------------------------------
// Read file header.
void
eqsimio::DataReader::readHeader(int32_t* pMeshID,
				int32_t* pSimID)
{ // readHeader
  const char* magicheader = "#EQSIM.data  "; // Expected magic header
  const int formatVersion = 2; // Expected format version
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
  
  // Read simulation ID
  int32_t simID;
  _pTranslator->translate(_fin, (char*) &simID, 1, sizeof(simID));
  if (!_fin.good())
    throw std::runtime_error("Could not read data ID.");

  // Set arguments
  if (0 != pMeshID)
    *pMeshID = meshID;
  if (0 != pSimID)
    *pSimID = simID;
} // readHeader
  
// ----------------------------------------------------------------------
// Read file header.
void
eqsimio::DataReader::readDataInfo(uint32_t* pNumTimeSteps,
				    float* pTimeStep,
				    uint32_t* pNumNodes,
				    uint32_t* pNumNodeVals,
				    uint32_t* pNumSets,
				    uint32_t** ppSetSizes,
				    uint32_t* pNumElemVals)
{ // readDataInfo
  assert(_fin.good());
  assert(0 != _pTranslator);

  // Read number of time steps
  uint32_t numTimeSteps;
  _pTranslator->translate(_fin, (char*) &numTimeSteps, 1, 
			  sizeof(numTimeSteps));
  if (!_fin.good())
    throw std::runtime_error("Could not read number of time steps.");
  
  // Read time step
  float timeStep;
  _pTranslator->translate(_fin, (char*) &timeStep, 1, sizeof(timeStep));
  if (!_fin.good())
    throw std::runtime_error("Could not read time step size.");

  // Read number of nodes
  uint32_t numNodes;
  _pTranslator->translate(_fin, (char*) &numNodes, 1, sizeof(numNodes));
  if (!_fin.good())
    throw std::runtime_error("Could not read number of nodes.");

  // Read number of values per node
  uint32_t numNodeVals;
  _pTranslator->translate(_fin, (char*) &numNodeVals, 1, sizeof(numNodeVals));
  if (!_fin.good())
    throw std::runtime_error("Could not read number of values per node.");

  // Read number of element sets
  uint32_t numSets;
  _pTranslator->translate(_fin, (char*) &numSets, 1, sizeof(numSets));
  if (!_fin.good())
    throw std::runtime_error("Could not read number of element sets.");
  uint32_t* pSetSizes = (numSets > 0) ? new uint32_t[numSets] : 0;

  // Read number of elements in each set
  _pTranslator->translate(_fin, (char*) pSetSizes, numSets, 
			  sizeof(*pSetSizes));
  if (!_fin.good())
    throw std::runtime_error("Could not read number of elements in each "
			     "element set.");
    
  // Read number of values per element
  uint32_t numElemVals;
  _pTranslator->translate(_fin, (char*) &numElemVals, 1, sizeof(numElemVals));
  if (!_fin.good())
    throw std::runtime_error("Could not read number of values per element.");

  // Save sizes of data chunks
  _sizeNodeVals = numNodes * numNodeVals;
  _numSets = numSets;
  _pSizeElemVals = (numSets > 0) ? new uint32_t[numSets] : 0;
  for (int i=0; i < numSets; ++i)
    _pSizeElemVals[i] = pSetSizes[i] * numElemVals;

  // Set arguments
  if (0 != pNumTimeSteps)
    *pNumTimeSteps = numTimeSteps;
  if (0 != pTimeStep)
    *pTimeStep = timeStep;
  if (0 != pNumNodes)
    *pNumNodes = numNodes;
  if (0 != pNumNodeVals)
    *pNumNodeVals = numNodeVals;
  if (0 != pNumSets)
    *pNumSets = numSets;
  if (0 != ppSetSizes)
    *ppSetSizes = pSetSizes;
  else {
    delete[] pSetSizes; pSetSizes = 0;
  } // else
  if (0 != pNumElemVals)
    *pNumElemVals = numElemVals;
} // readDataInfo
  
// ----------------------------------------------------------------------
// Read time.
void
eqsimio::DataReader::readTime(float* pTime)
{ // readTime
  assert(_fin.good());
  assert(0 != _pTranslator);

  float timeVal;
  _pTranslator->translate(_fin, (char*) &timeVal, 1, sizeof(timeVal));
  if (!_fin.good())
    throw std::runtime_error("Could not read number of values per element.");

  if (0 != pTime)
    *pTime = timeVal;
} // readTime

// ----------------------------------------------------------------------
// Read node values.
void
eqsimio::DataReader::readNodeVals(float** ppNodeVals)
{ // readNodeVals
  assert(_fin.good());
  assert(0 != _pTranslator);

  float* pVals = (_sizeNodeVals > 0) ? new float[_sizeNodeVals] : 0;
  _pTranslator->translate(_fin, (char*) pVals, _sizeNodeVals, sizeof(*pVals));
  if (!_fin.good())
    throw std::runtime_error("Could not read node values for time step.");

  if (0 != ppNodeVals)
    *ppNodeVals = pVals;
  else {
    delete[] pVals; pVals = 0;
  } // else
} // readNodeVals

// ----------------------------------------------------------------------
// Read element values.
void
eqsimio::DataReader::readElemVals(float*** pppElemVals)
{ // readElemVals
  assert(_fin.good());
  assert(0 != _pTranslator);

  const uint32_t numSets = _numSets;
  float** ppVals = (numSets > 0) ? new float*[numSets] : 0;
  for (int i=0; i < numSets; ++i) {
    assert(0 != _pSizeElemVals);
    const int size = _pSizeElemVals[i];
    ppVals[i] = (size > 0) ? new float[size] : 0;
    _pTranslator->translate(_fin, (char*) ppVals[i], size, sizeof(**ppVals));
  } // for
  if (!_fin.good())
    throw std::runtime_error("Could not read node values for time step.");

  if (0 != pppElemVals)
    *pppElemVals = ppVals;
  else {
    for (int i=0; i < numSets; ++i) {
      delete[] ppVals[i]; ppVals[i] = 0;
    } // for
    delete[] ppVals; ppVals = 0;
  } // else
} // readElemVals
  
// version
// $Id: DataReader.cc 2363 2006-01-29 22:30:23Z brad $

// End of file 
