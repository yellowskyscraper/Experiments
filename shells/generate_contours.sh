for a in *.dem
do
 echo "Processing $a"
 echo "Rendering at 50.00"
 /Library/Frameworks/GDAL.framework/Versions/1.9/Programs/gdal_contour -a ELEV -i 50.00 $a 50/$a.shp
 echo "Rendering at 200.00"
 /Library/Frameworks/GDAL.framework/Versions/1.9/Programs/gdal_contour -a ELEV -i 200.00 $a 200/$a.shp
done