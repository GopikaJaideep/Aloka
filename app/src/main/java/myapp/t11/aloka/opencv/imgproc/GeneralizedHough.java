//
// This file is auto-generated. Please don't modify it!
//
package myapp.t11.aloka.opencv.imgproc;

import myapp.t11.aloka.opencv.core.Algorithm;
import myapp.t11.aloka.opencv.core.Mat;
import myapp.t11.aloka.opencv.core.Point;

// C++: class GeneralizedHough
//javadoc: GeneralizedHough

public class GeneralizedHough extends Algorithm {

    protected GeneralizedHough(long addr) { super(addr); }

    // internal usage only
    public static GeneralizedHough __fromPtr__(long addr) { return new GeneralizedHough(addr); }

    //
    // C++:  double cv::GeneralizedHough::getDp()
    //

    //javadoc: GeneralizedHough::getDp()
    public  double getDp()
    {
        
        double retVal = getDp_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  double cv::GeneralizedHough::getMinDist()
    //

    //javadoc: GeneralizedHough::getMinDist()
    public  double getMinDist()
    {
        
        double retVal = getMinDist_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  int cv::GeneralizedHough::getCannyHighThresh()
    //

    //javadoc: GeneralizedHough::getCannyHighThresh()
    public  int getCannyHighThresh()
    {
        
        int retVal = getCannyHighThresh_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  int cv::GeneralizedHough::getCannyLowThresh()
    //

    //javadoc: GeneralizedHough::getCannyLowThresh()
    public  int getCannyLowThresh()
    {
        
        int retVal = getCannyLowThresh_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  int cv::GeneralizedHough::getMaxBufferSize()
    //

    //javadoc: GeneralizedHough::getMaxBufferSize()
    public  int getMaxBufferSize()
    {
        
        int retVal = getMaxBufferSize_0(nativeObj);
        
        return retVal;
    }


    //
    // C++:  void cv::GeneralizedHough::detect(Mat edges, Mat dx, Mat dy, Mat& positions, Mat& votes = Mat())
    //

    //javadoc: GeneralizedHough::detect(edges, dx, dy, positions, votes)
    public  void detect(Mat edges, Mat dx, Mat dy, Mat positions, Mat votes)
    {
        
        detect_0(nativeObj, edges.nativeObj, dx.nativeObj, dy.nativeObj, positions.nativeObj, votes.nativeObj);
        
        return;
    }

    //javadoc: GeneralizedHough::detect(edges, dx, dy, positions)
    public  void detect(Mat edges, Mat dx, Mat dy, Mat positions)
    {
        
        detect_1(nativeObj, edges.nativeObj, dx.nativeObj, dy.nativeObj, positions.nativeObj);
        
        return;
    }


    //
    // C++:  void cv::GeneralizedHough::detect(Mat image, Mat& positions, Mat& votes = Mat())
    //

    //javadoc: GeneralizedHough::detect(image, positions, votes)
    public  void detect(Mat image, Mat positions, Mat votes)
    {
        
        detect_2(nativeObj, image.nativeObj, positions.nativeObj, votes.nativeObj);
        
        return;
    }

    //javadoc: GeneralizedHough::detect(image, positions)
    public  void detect(Mat image, Mat positions)
    {
        
        detect_3(nativeObj, image.nativeObj, positions.nativeObj);
        
        return;
    }


    //
    // C++:  void cv::GeneralizedHough::setCannyHighThresh(int cannyHighThresh)
    //

    //javadoc: GeneralizedHough::setCannyHighThresh(cannyHighThresh)
    public  void setCannyHighThresh(int cannyHighThresh)
    {
        
        setCannyHighThresh_0(nativeObj, cannyHighThresh);
        
        return;
    }


    //
    // C++:  void cv::GeneralizedHough::setCannyLowThresh(int cannyLowThresh)
    //

    //javadoc: GeneralizedHough::setCannyLowThresh(cannyLowThresh)
    public  void setCannyLowThresh(int cannyLowThresh)
    {
        
        setCannyLowThresh_0(nativeObj, cannyLowThresh);
        
        return;
    }


    //
    // C++:  void cv::GeneralizedHough::setDp(double dp)
    //

    //javadoc: GeneralizedHough::setDp(dp)
    public  void setDp(double dp)
    {
        
        setDp_0(nativeObj, dp);
        
        return;
    }


    //
    // C++:  void cv::GeneralizedHough::setMaxBufferSize(int maxBufferSize)
    //

    //javadoc: GeneralizedHough::setMaxBufferSize(maxBufferSize)
    public  void setMaxBufferSize(int maxBufferSize)
    {
        
        setMaxBufferSize_0(nativeObj, maxBufferSize);
        
        return;
    }


    //
    // C++:  void cv::GeneralizedHough::setMinDist(double minDist)
    //

    //javadoc: GeneralizedHough::setMinDist(minDist)
    public  void setMinDist(double minDist)
    {
        
        setMinDist_0(nativeObj, minDist);
        
        return;
    }


    //
    // C++:  void cv::GeneralizedHough::setTemplate(Mat edges, Mat dx, Mat dy, Point templCenter = Point(-1, -1))
    //

    //javadoc: GeneralizedHough::setTemplate(edges, dx, dy, templCenter)
    public  void setTemplate(Mat edges, Mat dx, Mat dy, Point templCenter)
    {
        
        setTemplate_0(nativeObj, edges.nativeObj, dx.nativeObj, dy.nativeObj, templCenter.x, templCenter.y);
        
        return;
    }

    //javadoc: GeneralizedHough::setTemplate(edges, dx, dy)
    public  void setTemplate(Mat edges, Mat dx, Mat dy)
    {
        
        setTemplate_1(nativeObj, edges.nativeObj, dx.nativeObj, dy.nativeObj);
        
        return;
    }


    //
    // C++:  void cv::GeneralizedHough::setTemplate(Mat templ, Point templCenter = Point(-1, -1))
    //

    //javadoc: GeneralizedHough::setTemplate(templ, templCenter)
    public  void setTemplate(Mat templ, Point templCenter)
    {
        
        setTemplate_2(nativeObj, templ.nativeObj, templCenter.x, templCenter.y);
        
        return;
    }

    //javadoc: GeneralizedHough::setTemplate(templ)
    public  void setTemplate(Mat templ)
    {
        
        setTemplate_3(nativeObj, templ.nativeObj);
        
        return;
    }


    @Override
    protected void finalize() throws Throwable {
        delete(nativeObj);
    }



    // C++:  double cv::GeneralizedHough::getDp()
    private static native double getDp_0(long nativeObj);

    // C++:  double cv::GeneralizedHough::getMinDist()
    private static native double getMinDist_0(long nativeObj);

    // C++:  int cv::GeneralizedHough::getCannyHighThresh()
    private static native int getCannyHighThresh_0(long nativeObj);

    // C++:  int cv::GeneralizedHough::getCannyLowThresh()
    private static native int getCannyLowThresh_0(long nativeObj);

    // C++:  int cv::GeneralizedHough::getMaxBufferSize()
    private static native int getMaxBufferSize_0(long nativeObj);

    // C++:  void cv::GeneralizedHough::detect(Mat edges, Mat dx, Mat dy, Mat& positions, Mat& votes = Mat())
    private static native void detect_0(long nativeObj, long edges_nativeObj, long dx_nativeObj, long dy_nativeObj, long positions_nativeObj, long votes_nativeObj);
    private static native void detect_1(long nativeObj, long edges_nativeObj, long dx_nativeObj, long dy_nativeObj, long positions_nativeObj);

    // C++:  void cv::GeneralizedHough::detect(Mat image, Mat& positions, Mat& votes = Mat())
    private static native void detect_2(long nativeObj, long image_nativeObj, long positions_nativeObj, long votes_nativeObj);
    private static native void detect_3(long nativeObj, long image_nativeObj, long positions_nativeObj);

    // C++:  void cv::GeneralizedHough::setCannyHighThresh(int cannyHighThresh)
    private static native void setCannyHighThresh_0(long nativeObj, int cannyHighThresh);

    // C++:  void cv::GeneralizedHough::setCannyLowThresh(int cannyLowThresh)
    private static native void setCannyLowThresh_0(long nativeObj, int cannyLowThresh);

    // C++:  void cv::GeneralizedHough::setDp(double dp)
    private static native void setDp_0(long nativeObj, double dp);

    // C++:  void cv::GeneralizedHough::setMaxBufferSize(int maxBufferSize)
    private static native void setMaxBufferSize_0(long nativeObj, int maxBufferSize);

    // C++:  void cv::GeneralizedHough::setMinDist(double minDist)
    private static native void setMinDist_0(long nativeObj, double minDist);

    // C++:  void cv::GeneralizedHough::setTemplate(Mat edges, Mat dx, Mat dy, Point templCenter = Point(-1, -1))
    private static native void setTemplate_0(long nativeObj, long edges_nativeObj, long dx_nativeObj, long dy_nativeObj, double templCenter_x, double templCenter_y);
    private static native void setTemplate_1(long nativeObj, long edges_nativeObj, long dx_nativeObj, long dy_nativeObj);

    // C++:  void cv::GeneralizedHough::setTemplate(Mat templ, Point templCenter = Point(-1, -1))
    private static native void setTemplate_2(long nativeObj, long templ_nativeObj, double templCenter_x, double templCenter_y);
    private static native void setTemplate_3(long nativeObj, long templ_nativeObj);

    // native support for java finalize()
    private static native void delete(long nativeObj);

}
