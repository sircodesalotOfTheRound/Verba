using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;
using Microsoft.VisualStudio;
using Microsoft.VisualStudio.Shell.Interop;
using Microsoft.VisualStudio.OLE.Interop;
using Microsoft.VisualStudio.Shell;


namespace Verba
{
    [Guid(GuidList.guidVerbaPkgString)]
    public class VerbaPackage : VerbaPackageBase
    {
    }
}