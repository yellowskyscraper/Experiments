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

/** @file lib/GeometryReader.h
 *
 * @brief C++ GeometryReader object
 *
 * C++ object for reading EqSim geometry files.
 */

#if !defined(eqsimio_geometryreader_h)
#define eqsimio_geometryreader_h

namespace eqsimio {
  class GeometryReader;
  class StreamTranslator; // forward declaration
} // eqsimio

#include <stdint.h> // USES uint32_t
#include <iosfwd> // HOLDSA std::istream
#include <string> // USES std::string

/// C++ object for reading EqSim output.
class eqsimio::GeometryReader {
  friend class TestGeometryReader;
  
 public :
  // PUBLIC METHODS /////////////////////////////////////////////////////
  
  /** Constructor.
   *
   * @param fin Input stream
   */
  GeometryReader(std::istream& fin);
  
  /// Destructor
  ~GeometryReader(void);
  
  /** Read file header.
   *
   * @param pMeshID Pointer to mesh ID
   */
  void readHeader(int32_t* pMeshID);
  
  /** Read coordinates of nodes.
   *
   * @param pNumNodes Pointer to number of nodes
   * @param pNumCoords Pointer to number of coordinates per node
   * @param ppCoords Pointer to array of nodal coordinates
   */
  void readCoords(uint32_t* pNumNodes,
		  uint32_t* pNumCoords,
		  float** ppCoords);
  
  /** Read topology (element sets).
   *
   * @param pNumSets Pointer to number of element sets
   * @param ppSetSizes Pointer to array of number of elements in each set
   * @param ppSetNames Pointer to array of names of each element set
   * @param pConnSize Pointer to number of nodes in element
   * @param pppConns Pointer to array of arrays of element connectivities.
   */
  void readTopology(uint32_t* pNumSets,
		    uint32_t** ppSetSizes,
		    std::string** ppSetNames,
		    uint32_t* pConnSize,
		    uint32_t*** pppConns);
  
private :
  // PRIVATE METHODS ////////////////////////////////////////////////////
  
  GeometryReader(const GeometryReader& r); ///< Not implemented
  const GeometryReader& operator=(const GeometryReader& r); ///< Not implemented
  
private :
// PRIVATE MEMBERS ////////////////////////////////////////////////////
  
  std::istream& _fin; ///< Input stream
  StreamTranslator* _pTranslator; ///< Stream reader and endian translator
  
}; // GeometryReader

#endif // eqsimio_geometryreader_h

// version
// $Id: GeometryReader.h 2365 2006-01-29 22:49:01Z brad $

// End of file
