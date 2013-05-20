using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Verba
{
    static class GuidList
    {
        public const string guidVerbaPkgString = "02267df2-b5b3-4a2e-a5c1-0b3f0eb00d44";
        public const string guidCommandSetString = "41ac7d28-c3b4-42ac-9f7c-52c25677a757";

        public static readonly Guid guidVerbaCmdSet = new Guid(guidCommandSetString);
    };
}
