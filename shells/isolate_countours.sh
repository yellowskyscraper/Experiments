# Increment at 10.00 m
for ((  i = 0 ;  i <= 1400;  i+=10  )) 
do
echo "Processing gdal_contour -${i}.00 and ${i}.00 at 10m"

if [ ${i} == 0 ]; 
then
echo "No negative"
else
mkdir 10/negative/${i}
/Library/Frameworks/GDAL.framework/Versions/1.9/Programs/gdal_contour -a elev -fl -${i}.00 combined.dem 10/negative/${i}/${i}.shp
fi

mkdir 10/positive/${i}
/Library/Frameworks/GDAL.framework/Versions/1.9/Programs/gdal_contour -a elev -fl ${i}.00 combined.dem 10/positive/${i}/${i}.shp
echo "Done"
done

# Increment at 25.00 m
for ((  i = 0 ;  i <= 1400;  i+=25  )) 
do
echo "Processing gdal_contour -${i}.00 and ${i}.00 at 20m"

if [ ${i} == 0 ]; 
then
echo "No negative"
else
mkdir 25/negative/${i}
/Library/Frameworks/GDAL.framework/Versions/1.9/Programs/gdal_contour -a elev -fl -${i}.00 combined.dem 25/negative/${i}/${i}.shp
fi

mkdir 25/positive/${i}
/Library/Frameworks/GDAL.framework/Versions/1.9/Programs/gdal_contour -a elev -fl ${i}.00 combined.dem 25/positive/${i}/${i}.shp
echo "Done"
done

# Increment at 50.00 m
for ((  i = 0 ;  i <= 1400;  i+=50  )) 
do
echo "Processing gdal_contour -${i}.00 and ${i}.00 at 50m"

if [ ${i} == 0 ]; 
then
echo "No negative"
else
mkdir 50/negative/${i}
/Library/Frameworks/GDAL.framework/Versions/1.9/Programs/gdal_contour -a elev -fl -${i}.00 combined.dem 50/negative/${i}/${i}.shp
fi

mkdir 50/positive/${i}
/Library/Frameworks/GDAL.framework/Versions/1.9/Programs/gdal_contour -a elev -fl ${i}.00 combined.dem 50/positive/${i}/${i}.shp
echo "Done"
done

# Increment at 200.00 m
for ((  i = 0 ;  i <= 1400;  i+=200  )) 
do
echo "Processing gdal_contour -${i}.00 and ${i}.00 at 50m"

if [ ${i} == 0 ]; 
then
echo "No negative"
else
mkdir 200/negative/${i}
/Library/Frameworks/GDAL.framework/Versions/1.9/Programs/gdal_contour -a elev -fl -${i}.00 combined.dem 200/negative/${i}/${i}.shp
fi

mkdir 200/positive/${i}
/Library/Frameworks/GDAL.framework/Versions/1.9/Programs/gdal_contour -a elev -fl ${i}.00 combined.dem 200/positive/${i}/${i}.shp
echo "Done"
done
