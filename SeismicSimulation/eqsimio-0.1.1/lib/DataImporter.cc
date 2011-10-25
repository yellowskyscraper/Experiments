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

#include "DataImporter.h" // implementation of class methods

#include "DataReader.h" // USES DataReader

#include <fstream> // USES std::ifstream

#include <sstream> // USES std::ostringstream
#include <stdexcept> // USES std::runtime_error

// ----------------------------------------------------------------------
// Default constructor
eqsimio::DataImporter::DataImporter(void):
  _filename(""),
  _pReader(0)
{ // constructor
} // constructor

// ----------------------------------------------------------------------
// Default destructor
eqsimio::DataImporter::~DataImporter(void)
{ // destructor
  close();
} // destructor

// ----------------------------------------------------------------------
// Open data file and read header. All data is passed back through
// the arguments.
void
eqsimio::DataImporter::open(int32_t* pMeshID,
			    int32_t* pDataID,
			    uint32_t* pNumTimeSteps,
			    float* pTimeStep,
			    uint32_t* pNumNodes,
			    uint32_t* pNumNodeVals,
			    uint32_t* pNumSets,
			    uint32_t** ppSetSizes,
			    uint32_t* pNumElemVals)
{ // open
  // Open data file for reading
  std::ifstream fin(_filename.c_str());
  if (!fin.is_open() || !fin.good()) {
    std::ostringstream msg;
    msg << "Could not open EqSim data file '" << _filename
	<< "' for reading.";
    throw std::runtime_error(msg.str().c_str());
  } // if

  try {
    delete _pReader;
    _pReader = new DataReader(_filename.c_str());

    // Read header
    _pReader->readHeader(pMeshID, pDataID);

    // Read data information
    _pReader->readDataInfo(pNumTimeSteps, pTimeStep,
			   pNumNodes, pNumNodeVals,
			   pNumSets, ppSetSizes, pNumElemVals);
  } catch (std::exception& err) {
    std::ostringstream msg;
    msg << "Error occurred while opening EqSim data file '" << _filename
	<< "'.\n"
	<< "Error message: " << err.what();
    throw std::runtime_error(msg.str().c_str());
  } catch (...) {
    std::ostringstream msg;
    msg << "Unknown error occurred while opening EqSim data file '"
	<< _filename << "'.\n";
    throw std::runtime_error(msg.str().c_str());
  } // catch
} // open
  
// ----------------------------------------------------------------------
// Read time step. All data is passed back through the arguments.
void
eqsimio::DataImporter::readTimeStep(float* pTime,
				    float** ppNodeVals,
				    float*** pppElemVals)
{ // readTimeStep
  if (0 == _pReader) {
    std::ostringstream msg;
    msg << "Cannot read time step from EqSim data file '" << _filename
	<< "' without opening file first.\n"
	<< "Please call DataImporter::open() before "
	<< "DataImporter::readTimeStep().";
    throw std::logic_error(msg.str().c_str());
  } // if

  try {
    _pReader->readTime(pTime);
    _pReader->readNodeVals(ppNodeVals);
    _pReader->readElemVals(pppElemVals);
  } catch (std::exception& err) {
    std::ostringstream msg;
    msg << "Error occurred while reading time step from EqSim data file '"
	<< _filename
	<< "'.\n"
	<< "Error message: " << err.what();
    throw std::runtime_error(msg.str().c_str());
  } catch (...) {
    std::ostringstream msg;
    msg << "Unknown error occurred while reading time step from EqSim data "
	<< "file '" << _filename << "'.\n";
    throw std::runtime_error(msg.str().c_str());
  } // catch
} // readTimeStep

// ----------------------------------------------------------------------
/// Close data file.
void
eqsimio::DataImporter::close(void)
{ // close
  delete _pReader; _pReader = 0;
} // close

// version
// $Id: DataImporter.cc 2363 2006-01-29 22:30:23Z brad $

// End of file 
