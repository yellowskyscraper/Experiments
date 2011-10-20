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

/** @file lib/StreamTranslator.h
 *
 * @brief C++ StreamTranslator object
 *
 * C++ object for binary stream I/O and endian conversions.
 */

#if !defined(eqsimio_streamtranslator_h)
#define eqsimio_streamtranslator_h

namespace eqsimio {
  class StreamTranslator;
} // eqsimio

#include <iosfwd> // USES std::istream

/// C++ object for binary stream I/O and endian conversions.
class eqsimio::StreamTranslator {
  friend class TestStreamTranslator;

 public :
  // PUBLIC ENUMS ///////////////////////////////////////////////////////

  /// Endian enumerated types
  enum EndianType { 
    BIGENDIAN, ///< big endian
    LITTLEENDIAN ///< little endian
  };

 public :
  // PUBLIC METHODS /////////////////////////////////////////////////////

  /// Default constructor
  StreamTranslator(void);

  /// Default destructor
  ~StreamTranslator(void);

  /** Set endian type of stream.
   *
   * @name endian Endian type
   */
  void streamEndian(const EndianType endian);

  /** Read bytes from file and change endianness as necessary.
   *
   * @param fin Input stream
   * @param pData Pointer to first byte
   * @param numValues Number of values to read
   * @param valueSize Number of bytes per value
   */
  void translate(std::istream& fin,
		 char* pData,
		 const int numValues,
		 const int valueSize) const;
  
private :
  // PRIVATE METHODS ////////////////////////////////////////////////////
  
  StreamTranslator(const StreamTranslator& t); ///< Not implemented
  const StreamTranslator& operator=(const StreamTranslator& t); ///< Not implemented
    
private :
  // PRIVATE MEMBERS ////////////////////////////////////////////////////

  EndianType _nativeEndian;
  EndianType _streamEndian;

}; // StreamTranslator

#include "StreamTranslator.icc" // inline methods

#endif // eqsimio_endiantranslator_h

// version
// $Id: StreamTranslator.h 2363 2006-01-29 22:30:23Z brad $

// End of file
