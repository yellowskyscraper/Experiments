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

/** @file lib/DataImporter.h
 *
 * @brief C++ DataImporter object
 *
 * C++ object for importing EqSim data files (PUBLIC INTERFACE).
 */

#if !defined(eqsimio_dataimporter_h)
#define eqsimio_dataimporter_h

namespace eqsimio {
  class DataImporter;
  class DataReader; // forward declaration
} // eqsimio

#include <stdint.h> // USES uint32_t
#include <string> // HASA std::string

/// C++ object for importer EqSim output. (PUBLIC INTERFACE)
class eqsimio::DataImporter {
  friend class TestDataImporter;

 public :
  // PUBLIC METHODS /////////////////////////////////////////////////////

  /// Default constructor
  DataImporter(void);

  /// Default destructor
  ~DataImporter(void);

  /** Set filename of data file.
   *
   * @param name Name of file
   */
  void filename(const char* name);

  /** Open data file and read header. All data is passed back through
   * the arguments.
   *
   * @param pMeshID Pointer to mesh ID
   * @param pDataID Pointer to data ID
   * @param pNumTimeSteps Pointer to number of time steps
   * @param pTimeStep Pointer to time step
   * @param pNumNodes Pointer to number of nodes
   * @param pNumNodeVals Pointer to number of values per node
   * @param pNumSets Pointer to number of element sets
   * @param ppSetSizes Pointer to array of number of elements in each set
   * @param pNumElemVals Pointer to number of values per element
   */
  void open(int32_t* pMeshID,
	    int32_t* pDataID,
	    uint32_t* pNumTimeSteps,
	    float* pTimeStep,
	    uint32_t* pNumNodes,
	    uint32_t* pNumNodeVals,
	    uint32_t* pNumSets,
	    uint32_t** ppSetSizes,
	    uint32_t* pNumElemVals);
  
  /** Read time step. All data is passed back through the arguments.
   *
   * @param pTime Pointer to time of time step
   * @param ppNodeVals Pointer to array of node values
   * @param pppElemVals Pointer to array of element values
   */
  void readTimeStep(float* pTime,
		    float** ppNodeVals,
		    float*** pppElemVals);

  /// Close data file.
  void close(void);

private :
  // PRIVATE METHODS ////////////////////////////////////////////////////
  
  DataImporter(const DataImporter& i); ///< Not implemented
  const DataImporter& operator=(const DataImporter& i); ///< Not implemented
    
private :
  // PRIVATE MEMBERS ////////////////////////////////////////////////////

  std::string _filename; ///< Name of file

  DataReader* _pReader; ///< Reader for data files

}; // DataImporter

#include "DataImporter.icc" // inline methods

#endif // eqsimio_dataimporter_h

// version
// $Id: DataImporter.h 2365 2006-01-29 22:49:01Z brad $

// End of file
